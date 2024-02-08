package gyul.songgyubin.daytogo.viewmodels

import gyul.songgyubin.daytogo.location.model.LocationUiModel
import gyul.songgyubin.daytogo.location.viewmodel.LocationViewModel
import gyul.songgyubin.domain.location.model.LocationEntity
import gyul.songgyubin.domain.location.usecase.AddLocationInfoUseCase
import gyul.songgyubin.domain.location.usecase.GetRemoteSavedLocationInfoUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Location View Model Test
 *
 * @author   Gyub
 * @created  2024/02/08
 */
@ExperimentalCoroutinesApi
class LocationViewModelTest {

    private lateinit var viewModel: LocationViewModel
    private val addLocationInfoUseCase: AddLocationInfoUseCase = mockk()
    private val getRemoteSavedLocationInfoUseCase: GetRemoteSavedLocationInfoUseCase = mockk()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LocationViewModel(addLocationInfoUseCase, getRemoteSavedLocationInfoUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * 위도(lat)와 경도(lon)를 사용하여
     * 올바른 LocationId를 생성하는지 테스트
     */
    @Test
    fun `createLocationId generates correct LocationId`() = runBlocking {
        val lat = 37.422
        val lon = -122.084
        viewModel.createLocationId(lat, lon)
        assert(viewModel.selectedLocationId.value == "${lat}_$lon")
    }

    /**
     * LocationUiModel을 savedLocationUiModel에
     * 올바르게 업데이트하는지 테스트
     */
    @Test
    fun `setSavedLocationInfo updates savedLocationUiModel correctly`() = runBlocking {
        val locationUiModel = LocationUiModel("1", "Test Location", lat = 37.422, lon = -122.084)

        viewModel.setSavedLocationInfo(locationUiModel)
        assert(viewModel.savedLocationUiModel["1"] == locationUiModel)
    }
}