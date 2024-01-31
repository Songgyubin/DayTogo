package gyul.songgyubin.data.location.model

import com.google.firebase.database.DataSnapshot
import gyul.songgyubin.domain.location.model.LocationInfoEntity

object LocationInfoMapper {

    /**
     *
     */
    fun mapperToLocationInfo(dataSnapShotList: Iterable<DataSnapshot>): List<LocationInfoEntity> {
        return dataSnapShotList.map { it.value as HashMap<*, *> }
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