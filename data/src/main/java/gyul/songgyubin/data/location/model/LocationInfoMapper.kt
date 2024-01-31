package gyul.songgyubin.data.location.model

import com.google.firebase.database.DataSnapshot

object LocationInfoMapper {

    /**
     * Mapper
     * [DataSnapshot] to [LocationResponse]
     */
    fun mapperToLocationResponse(dataSnapShotList: Iterable<DataSnapshot>): List<LocationResponse> {
        return dataSnapShotList.map { it.value as HashMap<*, *> }
            .map { hashMap ->
                val locationResponse = LocationResponse()
                val clazz = LocationResponse::class.java
                val fields = clazz.declaredFields
                fields.map {
                    it.also { field -> field.isAccessible = true }
                }.forEach {
                    val value = hashMap[it.name]
                    it.set(locationResponse, value)
                }
                locationResponse
            }
    }

}