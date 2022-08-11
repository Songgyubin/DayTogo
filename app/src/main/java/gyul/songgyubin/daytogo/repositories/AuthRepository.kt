package gyul.songgyubin.daytogo.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import durdinapps.rxfirebase2.RxFirebaseAuth
import durdinapps.rxfirebase2.RxFirebaseDatabase
import gyul.songgyubin.daytogo.models.User
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class AuthRepository(context: Context) {

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

    fun createUserWithEmailAndPassword(
        auth: FirebaseAuth,
        inputEmail: String,
        inputPassword: String
    ): Maybe<User> {
       return RxFirebaseAuth.createUserWithEmailAndPassword(auth,inputEmail,inputPassword)
           .subscribeOn(Schedulers.io())
           .observeOn(Schedulers.io())
           .map { authResult->
               val firebaseUser = authResult.user!!
               val user = User(uid = firebaseUser.uid, email = firebaseUser.email!!)
               user
           }
           .observeOn(AndroidSchedulers.mainThread())
    }


}