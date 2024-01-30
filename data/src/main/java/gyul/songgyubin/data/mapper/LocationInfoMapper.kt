package gyul.songgyubin.data.mapper

import com.google.firebase.database.DataSnapshot
import gyul.songgyubin.domain.model.LocationInfoEntity

object LocationInfoMapper {

    fun mapperToLocationInfo(dataSnapShotList: Iterable<DataSnapshot>): List<LocationInfoEntity> {
        return dataSnapShotList.map { it.value as HashMap<String,Any> }
            .map { hashMap->
                val locationInfoEntity = LocationInfoEntity()
                val clazz = LocationInfoEntity::class.java
                val fields = clazz.declaredFields
                fields.map {
                    it.also { field -> field.isAccessible = true }
                }.forEach {
                    val value = hashMap[it.name]
                    it.set(locationInfoEntity, value)
                }
                locationInfoEntity
            }
    }
}