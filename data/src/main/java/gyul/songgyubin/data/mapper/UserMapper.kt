package gyul.songgyubin.data.mapper

import com.google.firebase.auth.FirebaseUser
import gyul.songgyubin.domain.auth.model.UserEntity

object UserMapper {
    fun mapperToUser(firebaseUser: FirebaseUser): UserEntity = UserEntity(
        uid = firebaseUser.uid,
        email = firebaseUser.email
    )
}