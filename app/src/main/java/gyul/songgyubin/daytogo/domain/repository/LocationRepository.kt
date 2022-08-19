package gyul.songgyubin.daytogo.domain.repository

import gyul.songgyubin.daytogo.domain.model.LocationInfo
import io.reactivex.Completable
import io.reactivex.Maybe

interface LocationRepository {
    fun getSavedLocationList(): Maybe<List<LocationInfo>>

    fun saveLocationDB(locationInfo: LocationInfo): Completable

}