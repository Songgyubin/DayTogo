package gyul.songgyubin.daytogo.repositories

import android.util.Log
import com.google.firebase.database.DatabaseReference
import durdinapps.rxfirebase2.RxFirebaseDatabase
import gyul.songgyubin.daytogo.mapper.LocationInfoMapper
import gyul.songgyubin.daytogo.models.LocationInfo
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainRepository {
    fun getSavedLocationList(
        dbReference: DatabaseReference,
        currentUid: String
    ): Maybe<List<LocationInfo>> {
        return RxFirebaseDatabase.observeSingleValueEvent(
            dbReference.child("users").child(currentUid).child("locationInfoList")
        ) { dataSnapShot ->
            return@observeSingleValueEvent dataSnapShot.children
        }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map { dataSnapShot ->
                dataSnapShot.map { it.value as HashMap<String,Any> }
            }
            .map { response ->
                response.map { locationInfo ->
                    LocationInfoMapper.firebaseResponseToLocationInfo(locationInfo)
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun saveLocationDB(
        dbReference: DatabaseReference,
        currentUid: String,
        locationInfo: LocationInfo
    ): Completable {
        return RxFirebaseDatabase.setValue(
            dbReference.child("users").child(currentUid).child("locationInfoList").push(),
            locationInfo
        )
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }

}