package gyul.songgyubin.data.location.model

import com.google.firebase.database.DataSnapshot

/**
 * Location Reponse
 *
 * @author   Gyub
 * @created  2024/01/31
 */
data class LocationResponse(
    val locationId: String? = "",
    val title: String? = "",
    val description: String? = "",
    val lat: String? = "",
    val lon: String? = ""
)