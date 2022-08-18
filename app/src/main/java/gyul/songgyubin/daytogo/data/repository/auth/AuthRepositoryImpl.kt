package gyul.songgyubin.daytogo.data.repository.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import durdinapps.rxfirebase2.RxFirebaseAuth
import durdinapps.rxfirebase2.RxFirebaseDatabase
import gyul.songgyubin.daytogo.domain.models.User
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AuthRepositoryImpl() {

    fun firebaseLogin(
        auth: FirebaseAuth,
        inputEmail: String,
        inputPassword: String
    ): Maybe<User> {
        return RxFirebaseAuth.signInWithEmailAndPassword(
            auth,
            inputEmail,
            inputPassword
        ).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map { authResult ->
                val firebaseUser = authResult.user!!
                val user = User(uid = firebaseUser.uid, email = firebaseUser.email!!)
                user
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun createUser(
        auth: FirebaseAuth,
        inputEmail: String,
        inputPassword: String
    ): Maybe<User> {
        return RxFirebaseAuth.createUserWithEmailAndPassword(auth, inputEmail, inputPassword)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map { authResult ->
                val firebaseUser = authResult.user!!
                val user = User(uid = firebaseUser.uid, email = firebaseUser.email!!)
                user
            }
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun createUserInfoDB(
        dbReference: DatabaseReference,
        user: User
    ):Completable {
        return RxFirebaseDatabase.setValue(dbReference.child("users").child(user.uid).child("userInfo"),user)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }
}