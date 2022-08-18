package gyul.songgyubin.daytogo.data.repository.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import durdinapps.rxfirebase2.RxFirebaseAuth
import durdinapps.rxfirebase2.RxFirebaseDatabase
import gyul.songgyubin.daytogo.data.mapper.UserMapper
import gyul.songgyubin.daytogo.domain.models.User
import gyul.songgyubin.daytogo.domain.repositories.AuthRepository
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AuthRepositoryImpl : AuthRepository {

    override fun firebaseLogin(
        auth: FirebaseAuth,
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
        auth: FirebaseAuth,
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
        dbReference: DatabaseReference,
        user: User
    ): Completable {
        return RxFirebaseDatabase.setValue(
            dbReference.child("users").child(user.uid).child("userInfo"), user
        )

    }
}