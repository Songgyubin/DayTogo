package gyul.songgyubin.domain.location.repository

import gyul.songgyubin.domain.location.model.LocationEntity
import gyul.songgyubin.domain.location.model.LocationRequest

/**
 * 장소 Repository
 */
interface LocationRepository {

    /**
     * 저장된 장소 리스트 가져오기
     */
    suspend fun getSavedLocationList(uid: String): List<LocationEntity>

    /**
     * 장소 저장하기
     */
    suspend fun saveLocationDB(uid: String, locationRequest: LocationRequest): Result<Unit>
}