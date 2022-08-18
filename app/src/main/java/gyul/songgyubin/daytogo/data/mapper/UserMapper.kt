package gyul.songgyubin.daytogo.data.mapper

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import gyul.songgyubin.daytogo.domain.models.User

object UserMapper {
    fun mapperToUser(firebaseUser: FirebaseUser):User = User().also {
        it.uid = firebaseUser.uid
        it.email = firebaseUser.email!!
    }


}