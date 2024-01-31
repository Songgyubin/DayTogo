package gyul.songgyubin.data.location.model

import gyul.songgyubin.domain.location.model.LocationEntity

object LocationMapper {

    /**
     * Mapper
     * [LocationResponse] to [LocationEntity]
     */
    fun LocationResponse.toEntity(): LocationEntity {
        return LocationEntity(
            locationId = locationId,
            title = title,
            description = description,
            lat = lat,
            lon = lon
        )
    }
}