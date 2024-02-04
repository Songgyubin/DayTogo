package gyul.songgyubin.domain.location.model

/**
 * Location Request
 *
 * @author   Gyub
 * @created  2024/01/31
 */
data class LocationRequest(
    val locationId: String? = "",
    val title: String? = "",
    val description: String? = "",
    val lat: Double? = 0.0,
    val lon: Double? = 0.0
)
