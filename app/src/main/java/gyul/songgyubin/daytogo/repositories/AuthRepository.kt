package gyul.songgyubin.daytogo.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import durdinapps.rxfirebase2.RxFirebaseAuth
import durdinapps.rxfirebase2.RxFirebaseDatabase
import gyul.songgyubin.daytogo.models.User
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AuthRepository() {

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

    fun createDB(
        dbReference: DatabaseReference,
        user: User
    ):Completable {
        return RxFirebaseDatabase.setValue(dbReference.child(user.uid),user)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }


}