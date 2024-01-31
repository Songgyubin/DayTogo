package gyul.songgyubin.data.auth.model

import com.google.firebase.auth.FirebaseUser
import gyul.songgyubin.domain.auth.model.UserEntity

/**
 * User Mapper
 */
object UserMapper {

    /**
     * Mapper
     * [FirebaseUser] to [UserResponse]
     */
    fun FirebaseUser?.toResponse(): UserResponse = UserResponse(
        uid = this?.uid,
        email = this?.email
    )

    /**
     * Mapper
     * [UserResponse] to [UserEntity]
     */
    fun UserResponse.toEntity(): UserEntity = UserEntity(
        uid = uid,
        email = email
    )
}