package gyul.songgyubin.daytogo.viewmodels

import com.google.firebase.auth.FirebaseAuth
import gyul.songgyubin.daytogo.location.model.LocationUiModel
import gyul.songgyubin.daytogo.location.viewmodel.LocationViewModel
import gyul.songgyubin.domain.location.model.LocationEntity
import gyul.songgyubin.domain.location.usecase.AddLocationInfoUseCase
import gyul.songgyubin.domain.location.usecase.GetRemoteSavedLocationInfoUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

/**
 * Location View Model Test
 *
 * @author   Gyub
 * @created  2024/02/08
 */
@ExperimentalCoroutinesApi
class LocationViewModelTest {

    private lateinit var viewModel: LocationViewModel

    private val auth: FirebaseAuth = mockk()

    private val addLocationInfoUseCase: AddLocationInfoUseCase = mockk()
    private val getRemoteSavedLocationInfoUseCase: GetRemoteSavedLocationInfoUseCase = mockk()


    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LocationViewModel(auth, addLocationInfoUseCase, getRemoteSavedLocationInfoUseCase)
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

    @Test
    fun `fetchSavedLocationList fetches data and updates state`() = runBlocking {
        val fakeUserId = "user123"
        val fakeLocationList = listOf(LocationEntity("1", "Home", null, 0.0, 0.0))

        every { auth.currentUser?.uid } returns fakeUserId
        every { getRemoteSavedLocationInfoUseCase(fakeUserId) } returns flowOf(fakeLocationList)

        viewModel.fetchSavedLocationList()

        assert(viewModel.savedLocationList.value == fakeLocationList)
    }
}