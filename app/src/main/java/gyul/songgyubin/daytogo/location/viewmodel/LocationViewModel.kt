package gyul.songgyubin.daytogo.location.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import gyul.songgyubin.daytogo.location.model.LocationUiModel
import gyul.songgyubin.daytogo.location.model.mapper.LocationUiModelMapper.toUiModelList
import gyul.songgyubin.domain.location.model.LocationRequest
import gyul.songgyubin.domain.location.usecase.AddLocationInfoUseCase
import gyul.songgyubin.domain.location.usecase.GetRemoteSavedLocationInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


/**
 * 위치 관련 ViewModel
 *
 * @author   Gyub
 * @created  2024/01/31
 */
@HiltViewModel
class LocationViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val addLocationInfoUseCase: AddLocationInfoUseCase,
    private val getRemoteSavedLocationInfoUseCase: GetRemoteSavedLocationInfoUseCase
) : ViewModel() {

    private val _savedLocationList = MutableStateFlow<List<LocationUiModel>>(listOf())
    val savedLocationList: StateFlow<List<LocationUiModel>> get() = _savedLocationList

    val savedLocationUiModel: HashMap<String, LocationUiModel> = HashMap()

    private val _savedLocationListErrorMsg = MutableStateFlow("")
    val savedLocationListErrorMsg: StateFlow<String> get() = _savedLocationListErrorMsg

    private val _selectedLocationId = MutableStateFlow("")
    val selectedLocationId: StateFlow<String> get() = _selectedLocationId

    private val _isEditMode = MutableStateFlow(false)
    val isEditMode: StateFlow<Boolean> get() = _isEditMode

    /**
     * 장소 구분을 위한
     * 클릭된 장소의 LocationId 생성
     */
    fun createLocationId(lat: Double, lon: Double) {
        _selectedLocationId.value = getLocationKey(lat, lon)
    }

    /**
     * 클릭된 장소의 loactionInfo를
     * LocationId라는 구분자로 보여주기 위해 Map에 세팅
     */
    fun setSavedLocationInfo(locationUiModel: LocationUiModel) {
        savedLocationUiModel[locationUiModel.locationId] = locationUiModel
    }

    /**
     * 저장된 위치 리스트 가져오기
     */
    fun fetchSavedLocationList() {
        val uid = auth.currentUser?.uid.orEmpty()

        getRemoteSavedLocationInfoUseCase(uid)
            .map { it.toUiModelList() }
            .onEach(::setUpSavedLocationList)
            .launchIn(viewModelScope)
    }

    /**
     * 저장된 위치 세팅
     */
    private fun setUpSavedLocationList(locationList: List<LocationUiModel>) {
        _savedLocationList.value = locationList
    }

    /**
     * 위치 정보 저장
     */
    fun savedLocationDB(locationRequest: LocationRequest) {
        val uid = auth.currentUser?.uid.orEmpty()

        _isEditMode.value = false

        addLocationInfoUseCase(uid, locationRequest)
            .onEach(::setUpSavedLocationResult)
            .launchIn(viewModelScope)
    }

    /**
     * 위치 저장 성공/실패 여부 세팅
     */
    private fun setUpSavedLocationResult(result: Result<Unit>) {
        when {
            result.isFailure -> {
                _savedLocationListErrorMsg.value = result.exceptionOrNull()?.localizedMessage.orEmpty()
            }
        }
    }

    /**
     * 편집 모드 세팅
     */
    fun setEditMode(view: View) {
        _isEditMode.value = true
    }

    /**
     * 장소 식별자 반환
     */
    private fun getLocationKey(lat: Double, lon: Double): String {
        return "${lat}_$lon"
    }
}
