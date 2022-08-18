package gyul.songgyubin.daytogo.models

/**
 * locationId: latitude.toString()_longitude.toString()
 */
data class LocationInfo(
    var locationId: String = "",
    var title: String = "",
    var description: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
)
