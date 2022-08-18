package gyul.songgyubin.daytogo.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import gyul.songgyubin.daytogo.base.viewmodel.BaseViewModel
import gyul.songgyubin.daytogo.domain.models.LocationInfo
import gyul.songgyubin.daytogo.domain.usecases.AddLocationInfoUseCase
import gyul.songgyubin.daytogo.domain.usecases.GetRemoteSavedLocationInfoUseCase
import gyul.songgyubin.daytogo.utils.LocationId
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers


/**
 * This class is SharedViewModel
 * with the classes included in the main package.
 * (MainActivity, LocationFragment...)
 */


class LocationViewModel(
    private val addLocationInfoUseCase: AddLocationInfoUseCase,
    private val getRemoteSavedLocationInfoUseCase: GetRemoteSavedLocationInfoUseCase
) : BaseViewModel() {

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
        _selectedLocationId.value = "${latitude}_$longitude"
    }

    fun setSavedLocationInfo(locationInfo: LocationInfo) {
        savedLocationInfo[locationInfo.locationId] = locationInfo
    }

    fun getSavedLocationList() {
        currentUser?.let {
            getRemoteSavedLocationInfoUseCase.invoke(
                dbReference, auth.currentUser!!.uid
            )
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showProgress() }
                .doAfterTerminate { hideProgress() }
                .subscribe({ locationList ->
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
        addLocationInfoUseCase.invoke(dbReference, currentUser!!.uid, locationInfo)
            .observeOn(Schedulers.io())
            .subscribe(
                {
                    Log.d("TAG", "savedLocationListDB: Success ")
                }, { error ->
                    Log.e("TAG", "savedLocationListDB: ", error)
                }
            )
            .addTo(disposable)
    }


    class ViewModelFactory(private val addLocationInfoUseCase: AddLocationInfoUseCase,
    private val getRemoteSavedLocationInfoUseCase: GetRemoteSavedLocationInfoUseCase) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LocationViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LocationViewModel(addLocationInfoUseCase,getRemoteSavedLocationInfoUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

}