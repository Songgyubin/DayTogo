package gyul.songgyubin.data.location.source

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import gyul.songgyubin.domain.location.model.LocationRequest
import gyul.songgyubin.data.location.model.LocationResponse
import kotlinx.coroutines.tasks.await

/**
 * 장소 정보 관련 Data Source
 *
 * @author   Gyub
 * @created  2024/01/31
 */
class LocationDataSource {
    private val dbReference by lazy { Firebase.database.reference }
    private val auth: FirebaseAuth by lazy { Firebase.auth }
    private val currentUser by lazy { auth.currentUser }

    /**
     * 저장된 장소 가져오기
     *
     * @return 저장된 장소 리스트
     */
    suspend fun getSavedLocationList(): List<LocationResponse> {
        val uid = currentUser?.uid.orEmpty()
        val snapshot = dbReference.child("users")
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
    suspend fun saveLocationDB(locationRequest: LocationRequest): Result<Unit> {
        return try {
            dbReference.child("users")
                .child(currentUser!!.uid)
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
