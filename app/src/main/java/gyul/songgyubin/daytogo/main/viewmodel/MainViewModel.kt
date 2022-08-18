package gyul.songgyubin.daytogo.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.naver.maps.geometry.LatLng
import gyul.songgyubin.daytogo.base.viewmodel.BaseViewModel
import gyul.songgyubin.daytogo.models.LocationInfo
import gyul.songgyubin.daytogo.repositories.MainRepository
import io.reactivex.rxkotlin.addTo


/**
 * This class is SharedViewModel
 * with the classes included in the main package.
 * (MainActivity, LocationFragment...)
 */

typealias LocationId = String

class MainViewModel(private val repository: MainRepository) : BaseViewModel() {
    private val dbReference by lazy { Firebase.database.reference }
    private val auth: FirebaseAuth by lazy { Firebase.auth }
    private val currentUser by lazy { auth.currentUser }

    val savedLocationList: LiveData<List<LocationInfo>> get() = _savedLocationList
    private val _savedLocationList = MutableLiveData<List<LocationInfo>>()

    val savedLocationInfo: HashMap<LocationId, LocationInfo> = HashMap()

    val savedLocationListErrorMsg: LiveData<String> get() = _savedLocationListErrorMsg
    private val _savedLocationListErrorMsg = MutableLiveData<String>()

    val selectedLocationId: LiveData<LocationId> get() = _selectedLocationId
    private val _selectedLocationId = MutableLiveData<LocationId>()


    fun selectLocation(latitude: Double, longitude: Double) {
        Log.d("TAG", "selectLocation: ${latitude}_$longitude")
        _selectedLocationId.value = "${latitude}_$longitude"
    }

    fun setSavedLocationInfo(locationInfo: LocationInfo) {
        savedLocationInfo[locationInfo.locationId] = locationInfo
    }

    fun getSavedLocationList() {

        if (currentUser == null) {
            _savedLocationListErrorMsg.value = "do not load user information"
            Log.e("TAG", "do not load user information")
        } else {
            repository.getSavedLocationList(
                dbReference, auth.currentUser!!.uid
            ).subscribe({ locationList ->
                _savedLocationList.value = locationList
                Log.d("TAG", "getSavedLocationList: ${locationList.size}")
            }, { error ->
                _savedLocationListErrorMsg.value = error.message
                Log.e("TAG", "getSavedLocationList: ", error)
            }
            ).addTo(disposable)
        }
    }

    fun savedLocationDB(locationInfo: LocationInfo) {
        repository.saveLocationDB(dbReference, currentUser!!.uid, locationInfo)
            .subscribe(
                {
                    Log.d("TAG", "savedLocationListDB: Success ")
                }, { error ->
                    Log.e("TAG", "savedLocationListDB: ", error)
                }
            ).addTo(disposable)
    }


    class ViewModelFactory(private val repository: MainRepository) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}