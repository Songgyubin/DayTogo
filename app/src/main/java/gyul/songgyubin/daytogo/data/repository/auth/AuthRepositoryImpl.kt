package gyul.songgyubin.daytogo.data.repository.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import durdinapps.rxfirebase2.RxFirebaseAuth
import durdinapps.rxfirebase2.RxFirebaseDatabase
import gyul.songgyubin.daytogo.data.mapper.UserMapper
import gyul.songgyubin.daytogo.domain.model.User
import gyul.songgyubin.daytogo.domain.repository.AuthRepository
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers

class AuthRepositoryImpl : AuthRepository {
    private val auth: FirebaseAuth by lazy { Firebase.auth }
    private val dbReference by lazy { Firebase.database.reference }

    override fun firebaseLogin(
        inputEmail: String,
        inputPassword: String
    ): Maybe<User> {
        return RxFirebaseAuth.signInWithEmailAndPassword(
            auth,
            inputEmail,
            inputPassword
        )
            .observeOn(Schedulers.io())
            .map { authResult ->
                UserMapper.mapperToUser(authResult.user!!)
            }

    }

    override fun createUser(
        inputEmail: String,
        inputPassword: String
    ): Maybe<User> {
        return RxFirebaseAuth.createUserWithEmailAndPassword(auth, inputEmail, inputPassword)
            .observeOn(Schedulers.io())
            .map { authResult ->
                UserMapper.mapperToUser(authResult.user!!)
            }

    }

    override fun createUserInfoDB(
        user: User
    ): Completable {
        return RxFirebaseDatabase.setValue(
            dbReference.child("users").child(user.uid).child("userInfo"), user
        )

    }
}