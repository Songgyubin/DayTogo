package gyul.songgyubin.daytogo.mapper

import android.text.TextUtils.split
import android.util.Log
import gyul.songgyubin.daytogo.models.LocationInfo
import gyul.songgyubin.daytogo.models.User

object LocationInfoMapper {

    fun firebaseResponseToLocationInfo(hashMap: HashMap<String, Any>): LocationInfo {
        val locationInfo = LocationInfo()
        val clazz = LocationInfo::class.java
        val fields = clazz.declaredFields
        fields.map {
            it.also { field -> field.isAccessible = true }
        }.forEach {
            val value = hashMap[it.name]
            it.set(locationInfo, value)
        }
        return locationInfo
    }
}