package gyul.songgyubin.daytogo.utils

import com.naver.maps.geometry.LatLng
import gyul.songgyubin.daytogo.main.viewmodel.LocationId

fun LocationId.toLatLng(): LatLng {
    val (lat, lng) = this.split("_").map { it.toDouble() }
    return LatLng(lat,lng)
}