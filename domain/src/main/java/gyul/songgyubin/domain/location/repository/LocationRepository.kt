package gyul.songgyubin.domain.repository

import gyul.songgyubin.domain.location.model.LocationEntity
import io.reactivex.Completable
import io.reactivex.Maybe

/**
 * 기억하는 장소 로직 관련
 */
interface LocationRepository {
    fun getSavedLocationList(): Maybe<List<LocationEntity>>

    fun saveLocationDB(locationEntity: LocationEntity): Completable

}