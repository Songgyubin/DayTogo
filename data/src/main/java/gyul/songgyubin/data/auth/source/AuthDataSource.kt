package gyul.songgyubin.data.auth.source

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import gyul.songgyubin.data.auth.model.UserMapper.toResponse
import gyul.songgyubin.data.auth.model.UserResponse
import gyul.songgyubin.domain.auth.model.UserEntity
import kotlinx.coroutines.tasks.await

/**
 * Auth Data Source
 *
 * @author   Gyub
 * @created  2024/01/31
 */
class AuthDataSource {
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val dbReference by lazy { FirebaseDatabase.getInstance().reference }

    /**
     * 파이어베이스 로그인
     */
    suspend fun firebaseLogin(inputEmail: String, inputPassword: String): UserResponse {
        return auth.signInWithEmailAndPassword(inputEmail, inputPassword)
            .await()
            .user
            .toResponse()
    }

    /**
     * User 정보 생성
     */
    suspend fun createUser(inputEmail: String, inputPassword: String): UserResponse {
        return auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
            .await()
            .user
            .toResponse()
    }

    /**
     * User 정보 DB에 저장
     */
    suspend fun saveUserInfoDB(user: UserEntity): Result<Unit> {
        return try {
            dbReference.child("users")
                .child(user.uid.orEmpty())
                .child("userInfo")
                .setValue(user)
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}