package gyul.songgyubin.domain.model


/**
 * 실제 사용하는 Data Class
 */
/**
 * locationId: latitude.toString()_longitude.toString()
 */

data class LocationInfoEntity(
    var locationId: String = "",
    var title: String = "",
    var description: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
)
