package gyul.songgyubin.daytogo.data.repository.location

import com.google.firebase.database.DatabaseReference
import durdinapps.rxfirebase2.RxFirebaseDatabase
import gyul.songgyubin.daytogo.data.mapper.LocationInfoMapper
import gyul.songgyubin.daytogo.domain.model.LocationInfo
import gyul.songgyubin.daytogo.domain.repository.LocationRepository
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers

class LocationRepositoryImpl : LocationRepository {
    override fun getSavedLocationList(
        dbReference: DatabaseReference,
        uid: String
    ): Maybe<List<LocationInfo>> {
        return RxFirebaseDatabase.observeSingleValueEvent(
            dbReference.child("users").child(uid).child("locationInfoList")
        ) { dataSnapShot ->
            return@observeSingleValueEvent dataSnapShot.children
        }
            .observeOn(Schedulers.computation())
            .map {
                LocationInfoMapper.mapperToLocationInfo(it)
            }
    }

    override fun saveLocationDB(
        dbReference: DatabaseReference,
        uid: String,
        locationInfo: LocationInfo
    ): Completable {
        return RxFirebaseDatabase.setValue(
            dbReference.child("users").child(uid).child("locationInfoList").push(),
            locationInfo
        )


    }

}