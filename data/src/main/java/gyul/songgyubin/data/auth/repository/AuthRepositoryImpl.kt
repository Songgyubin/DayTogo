package gyul.songgyubin.data.auth.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import durdinapps.rxfirebase2.RxFirebaseAuth
import durdinapps.rxfirebase2.RxFirebaseDatabase
import gyul.songgyubin.data.auth.model.UserMapper
import gyul.songgyubin.domain.auth.model.UserEntity
import gyul.songgyubin.domain.repository.AuthRepository
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor() : AuthRepository {
    private val auth: FirebaseAuth by lazy { Firebase.auth }
    private val dbReference by lazy { Firebase.database.reference }

    override fun firebaseLogin(
        inputEmail: String,
        inputPassword: String
    ): Maybe<UserEntity> {
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
    ): Maybe<UserEntity> {
        return RxFirebaseAuth.createUserWithEmailAndPassword(auth, inputEmail, inputPassword)
            .observeOn(Schedulers.io())
            .map { authResult ->
                UserMapper.mapperToUser(authResult.user!!)
            }

    }

    override fun createUserInfoDB(
        user: UserEntity
    ): Completable {
        return RxFirebaseDatabase.setValue(
            dbReference.child("users").child(user.uid.orEmpty()).child("userInfo"), user
        )

    }
}