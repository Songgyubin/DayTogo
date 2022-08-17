package gyul.songgyubin.daytogo.mapper

import gyul.songgyubin.daytogo.models.LocationInfo

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