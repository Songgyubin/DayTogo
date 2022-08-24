package gyul.songgyubin.data.mapper

import com.google.firebase.auth.FirebaseUser
import gyul.songgyubin.domain.model.User

object UserMapper {
    fun mapperToUser(firebaseUser: FirebaseUser): User = User(
        uid = firebaseUser.uid,
        email = firebaseUser.email!!
    )


}