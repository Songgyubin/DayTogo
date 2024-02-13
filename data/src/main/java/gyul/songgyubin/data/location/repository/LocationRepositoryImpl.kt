package gyul.songgyubin.data.location.repository

import gyul.songgyubin.data.location.model.LocationMapper.toEntity
import gyul.songgyubin.data.location.source.LocationDataSource
import gyul.songgyubin.domain.location.model.LocationEntity
import gyul.songgyubin.domain.location.model.LocationRequest
import gyul.songgyubin.domain.location.repository.LocationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepositoryImpl
@Inject constructor(
    private val locationDataSource: LocationDataSource
) : LocationRepository {

    /**
     * 저장된 장소 리스트 가져오기
     */
    override suspend fun getSavedLocationList(uid: String): List<LocationEntity> {
        return locationDataSource.getSavedLocationList(uid).map {
            it.toEntity()
        }
    }

    /**
     * 장소 저장하기
     */
    override suspend fun saveLocationDB(
        uid: String,
        locationRequest: LocationRequest
    ): Result<Unit> {
        return locationDataSource.saveLocationDB(uid, locationRequest)
    }
}