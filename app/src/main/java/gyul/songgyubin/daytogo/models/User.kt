package gyul.songgyubin.daytogo.models

import com.naver.maps.geometry.LatLng
import gyul.songgyubin.daytogo.utils.Category
import java.io.Serializable


data class User(
    val uid: String,
    val email: String,
    val locationList: MutableList<Location> = mutableListOf(Location())
):Serializable
//t@n.com
//000000