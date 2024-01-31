package gyul.songgyubin.data.location.repository

import gyul.songgyubin.data.location.model.LocationMapper.toEntity
import gyul.songgyubin.domain.location.model.LocationRequest
import gyul.songgyubin.data.location.source.LocationDataSource
import gyul.songgyubin.domain.location.model.LocationEntity
import gyul.songgyubin.domain.location.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl
@Inject constructor(
    private val locationDataSource: LocationDataSource
) : LocationRepository {

    /**
     * 저장된 장소 리스트 가져오기
     */
    override suspend fun getSavedLocationList(): List<LocationEntity> {
        return locationDataSource.getSavedLocationList().map {
            it.toEntity()
        }
    }

    /**
     * 장소 저장하기
     */
    override suspend fun saveLocationDB(
        locationRequest: LocationRequest
    ): Result<Unit> {
        return locationDataSource.saveLocationDB(locationRequest)
    }
}