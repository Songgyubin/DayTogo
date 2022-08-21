package gyul.songgyubin.domain.repository

import gyul.songgyubin.domain.model.LocationInfo
import io.reactivex.Completable
import io.reactivex.Maybe

interface LocationRepository {
    fun getSavedLocationList(): Maybe<List<LocationInfo>>

    fun saveLocationDB(locationInfo: LocationInfo): Completable

}