package gyul.songgyubin.daytogo.domain.repositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import gyul.songgyubin.daytogo.domain.models.LocationInfo
import io.reactivex.Completable
import io.reactivex.Maybe

interface LocationRepository {
    fun getSavedLocationList(
        dbReference: DatabaseReference,
        uid: String
    ): Maybe<List<LocationInfo>>

    fun saveLocationDB(
        dbReference: DatabaseReference,
        uid: String,
        locationInfo: LocationInfo
    ): Completable

}