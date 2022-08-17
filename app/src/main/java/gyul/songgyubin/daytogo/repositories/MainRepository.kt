package gyul.songgyubin.daytogo.repositories

import android.location.Location
import android.util.Log
import com.google.firebase.database.DatabaseReference
import durdinapps.rxfirebase2.RxFirebaseDatabase
import gyul.songgyubin.daytogo.mapper.LocationInfoMapper
import gyul.songgyubin.daytogo.models.LocationInfo
import gyul.songgyubin.daytogo.models.User
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
            return@observeSingleValueEvent dataSnapShot.value
        }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map { response->
                val locationInfoList = response as List<HashMap<String,Any>>
                locationInfoList.map {  hashMap->
                    LocationInfoMapper.firebaseResponseToLocationInfo(hashMap)
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

}