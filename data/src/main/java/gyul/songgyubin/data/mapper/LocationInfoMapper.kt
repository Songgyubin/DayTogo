package gyul.songgyubin.data.mapper

import com.google.firebase.database.DataSnapshot
import gyul.songgyubin.domain.model.LocationInfo

object LocationInfoMapper {

    fun mapperToLocationInfo(dataSnapShotList: Iterable<DataSnapshot>): List<LocationInfo> {
        return dataSnapShotList.map { it.value as HashMap<String,Any> }
            .map { hashMap->
                val locationInfo = LocationInfo()
                val clazz = LocationInfo::class.java
                val fields = clazz.declaredFields
                fields.map {
                    it.also { field -> field.isAccessible = true }
                }.forEach {
                    val value = hashMap[it.name]
                    it.set(locationInfo, value)
                }
                locationInfo
            }
    }
}