package gyul.songgyubin.daytogo.utils

import com.naver.maps.geometry.LatLng

/**
 * 위도, 경도 형식인 String 값으로 NaverMap의 LatLng 객체 생성
 */
fun String.toLatLng(): LatLng {
    val (lat, lng) = this.split("_").map { it.toDoubleOrNull() ?: 0.0 }
    return LatLng(lat, lng)
}