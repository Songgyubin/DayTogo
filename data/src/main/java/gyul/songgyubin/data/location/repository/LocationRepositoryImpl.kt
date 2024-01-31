package gyul.songgyubin.data.location.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import durdinapps.rxfirebase2.RxFirebaseDatabase
import gyul.songgyubin.data.location.model.LocationInfoMapper
import gyul.songgyubin.domain.location.model.LocationInfoEntity
import gyul.songgyubin.domain.repository.LocationRepository
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers

class LocationRepositoryImpl : LocationRepository {
    private val dbReference by lazy { Firebase.database.reference }
    private val auth: FirebaseAuth by lazy { Firebase.auth }
    private val currentUser by lazy { auth.currentUser }

    override fun getSavedLocationList(): Maybe<List<LocationInfoEntity>> {
        return RxFirebaseDatabase.observeSingleValueEvent(
            dbReference.child("users").child(currentUser!!.uid).child("locationInfoList")
        ) { dataSnapShot ->
            return@observeSingleValueEvent dataSnapShot.children
        }
            .observeOn(Schedulers.computation())
            .map {
                LocationInfoMapper.mapperToLocationInfo(it)
            }
    }

    override fun saveLocationDB(
        locationInfoEntity: LocationInfoEntity
    ): Completable {
        return RxFirebaseDatabase.setValue(
            dbReference.child("users").child(currentUser!!.uid).child("locationInfoList").push(),
            locationInfoEntity
        )
    }
}