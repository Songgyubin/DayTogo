package gyul.songgyubin.daytogo.location.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gyul.songgyubin.daytogo.utils.LocationId
import gyul.songgyubin.domain.location.model.LocationEntity
import gyul.songgyubin.domain.location.usecase.AddLocationInfoUseCase
import gyul.songgyubin.domain.location.usecase.GetRemoteSavedLocationInfoUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


/**
 * This class is SharedViewModel
 * with the classes included in the main package.
 * (MainActivity, LocationFragment...)
 */

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val addLocationInfoUseCase: AddLocationInfoUseCase,
    private val getRemoteSavedLocationInfoUseCase: GetRemoteSavedLocationInfoUseCase
) : ViewModel() {

    val savedLocationList: LiveData<List<LocationEntity>> get() = _savedLocationList
    private val _savedLocationList = MutableLiveData<List<LocationEntity>>()

    val savedLocationEntity: HashMap<LocationId, LocationEntity> = HashMap()

    val savedLocationListErrorMsg: LiveData<String> get() = _savedLocationListErrorMsg
    private val _savedLocationListErrorMsg = MutableLiveData<String>()

    val selectedLocationId: LiveData<LocationId> get() = _selectedLocationId
    private val _selectedLocationId = MutableLiveData<LocationId>()

    val isEditMode: LiveData<Boolean> get() = _isEditMode
    private val _isEditMode = MutableLiveData<Boolean>(false)


    /**
     * 장소를 구분하기위해
     * 클릭된 장소의 LocationId 생성
     */
    fun createLocationId(latitude: Double, longitude: Double) {
        _selectedLocationId.value = "${latitude}_$longitude"
    }

    /**
     * 클릭된 장소의 loactionInfo를
     * LocationId라는 구분자로 보여주기 위해 Map에 세팅
     */
    fun setSavedLocationInfo(locationEntity: LocationEntity) {
        savedLocationEntity[locationEntity.locationId] = locationEntity
    }

    fun getSavedLocationList() {
        getRemoteSavedLocationInfoUseCase()
            .onEach {  }
            .launchIn(viewModelScope)
    }

    fun savedLocationDB(locationEntity: LocationEntity) {
        _isEditMode.value = false
        addLocationInfoUseCase(locationEntity)
            .onEach {

            }.launchIn(viewModelScope)
    }

    fun setEditMode(view: View) {
        _isEditMode.value = true
    }
}