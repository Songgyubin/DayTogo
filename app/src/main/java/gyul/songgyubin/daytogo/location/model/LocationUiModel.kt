package gyul.songgyubin.daytogo.location.model

/**
 * Location Ui Model
 *
 * @author   Gyub
 * @created  2024/02/06
 */
data class LocationUiModel(
    val locationId: String = "",
    val title: String = "",
    val description: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0
)