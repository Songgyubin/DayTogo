package gyul.songgyubin.daytogo.models

import com.naver.maps.geometry.LatLng
import gyul.songgyubin.daytogo.utils.Category

data class Location(
    val category: Category,
    val title: String,
    val description:String,
    val latLng: LatLng,
)