package gyul.songgyubin.daytogo.location.model.mapper

import gyul.songgyubin.daytogo.location.model.LocationUiModel
import com.gyub.common.util.extensions.orDefault
import gyul.songgyubin.domain.location.model.LocationEntity

/**
 * Location Ui Model와 관련된 Mapper
 *
 * @author   Gyub
 * @created  2024/02/07
 */
object LocationUiModelMapper {

    /**
     * Mapper
     * List<[LocationEntity]> to List<[LocationUiModel]>
     */
    fun List<LocationEntity>.toUiModelList(): List<LocationUiModel> {
        return this.map {
            it.toUiModel()
        }
    }

    /**
     * Mapper
     * [LocationEntity] to [LocationUiModel]
     */
    fun LocationEntity.toUiModel(): LocationUiModel {
        return LocationUiModel(
            locationId = this.locationId.orEmpty(),
            title = this.title.orEmpty(),
            description = this.description.orEmpty(),
            lat = this.lat.orDefault(),
            lon = this.lon.orDefault()
        )
    }
}