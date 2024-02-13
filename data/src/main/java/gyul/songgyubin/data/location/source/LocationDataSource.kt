package gyul.songgyubin.data.location.source

import com.google.firebase.FirebaseException
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import gyul.songgyubin.data.location.model.LocationResponse
import gyul.songgyubin.domain.location.model.LocationRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * 장소 정보 관련 Data Source
 *
 * @author   Gyub
 * @created  2024/01/31
 */
class LocationDataSource
@Inject
constructor(
    private val firebaseDatabase: FirebaseDatabase,
) {


    /**
     * 저장된 장소 가져오기
     *
     * @return 저장된 장소 리스트
     */
    suspend fun getSavedLocationList(uid: String): List<LocationResponse> {
        val snapshot = firebaseDatabase.reference.child("users")
            .child(uid)
            .child("locationInfoList")
            .get()
            .await()

        return snapshot.children.mapNotNull { it.getValue<LocationResponse>() }
    }

    /**
     * 장소 저장하기
     *
     * @return 성공/실패 여부
     */
    suspend fun saveLocationDB(uid: String, locationRequest: LocationRequest): Result<Unit> {
        return try {
            firebaseDatabase.reference.child("users")
                .child(uid)
                .child("locationInfoList")
                .push()
                .setValue(locationRequest)
                .await()

            Result.success(Unit)
        } catch (e: FirebaseException) {
            Result.failure(e)
        }
    }
}
