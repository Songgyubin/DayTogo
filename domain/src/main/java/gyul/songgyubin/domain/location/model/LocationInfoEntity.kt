package gyul.songgyubin.domain.location.model

/**
 * Location Info Entity
 *
 * cf) locationId: latitude.toString()_longitude.toString()
 */
data class LocationInfoEntity(
    var locationId: String = "",
    var title: String = "",
    var description: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
)
