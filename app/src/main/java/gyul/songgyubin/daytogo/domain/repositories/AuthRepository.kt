package gyul.songgyubin.daytogo.domain.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import gyul.songgyubin.daytogo.domain.models.User
import io.reactivex.Completable
import io.reactivex.Maybe

interface AuthRepository {
    fun firebaseLogin(
        auth: FirebaseAuth,
        inputEmail: String,
        inputPassword: String
    ): Maybe<User>

    fun createUser(
        auth: FirebaseAuth,
        inputEmail: String,
        inputPassword: String
    ): Maybe<User>

    fun createUserInfoDB(
        dbReference: DatabaseReference,
        user: User
    ): Completable
}