package gyul.songgyubin.data.location.model

/**
 * Location Reponse
 *
 * @author   Gyub
 * @created  2024/01/31
 */
data class LocationResponse(
    val locationId: String?,
    val title: String?,
    val description: String?,
    val lat: Double?,
    val lon: Double?
)