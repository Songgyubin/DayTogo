package gyul.songgyubin.daytogo.mapper

import gyul.songgyubin.domain.model.LocationInfoEntity
import org.hamcrest.MatcherAssert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class LocationInfoEntityMapperTest {
    private val hashMap = HashMap<String, Any>()

    @Before
    fun setUp() {
        hashMap["title"] = "타이틀"
        hashMap["description"] = "설명"
        hashMap["latitude"] = 1.1
        hashMap["longitude"] = 0.0
    }

    @Test
    fun firebaseResponseToLocationInfo() {
        val locationInfoEntity = LocationInfoEntity()
        val clazz = LocationInfoEntity::class.java
        val methods = clazz.declaredMethods
        methods.forEach { it.isAccessible = true }
        val fields = clazz.declaredFields
        fields.forEach {
            it.isAccessible = true
            val value = hashMap[it.name]
            it.set(locationInfoEntity,value)
        }
        assertThat(locationInfoEntity.toString(), true).equals(LocationInfoEntity("","타이틀","설명",1.1,0.0).toString())
    }
}