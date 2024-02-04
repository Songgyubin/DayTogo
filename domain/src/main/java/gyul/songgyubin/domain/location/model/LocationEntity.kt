package gyul.songgyubin.domain.location.model

/**
 * Location Entity
 *
 * cf) locationId: latitude.toString()_longitude.toString()
 */
data class LocationEntity(
    val locationId: String?,
    val title: String?,
    val description: String?,
    val lat: Double?,
    val lon: Double?
)
