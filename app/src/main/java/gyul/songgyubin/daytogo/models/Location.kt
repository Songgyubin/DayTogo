package gyul.songgyubin.daytogo.models

import com.naver.maps.geometry.LatLng
import gyul.songgyubin.daytogo.utils.Category

data class Location(
    val category: Category = Category.NONE,
    val title: String = "",
    val description: String = "",
    val latLng: LatLng = LatLng(0.0, 0.0),
)