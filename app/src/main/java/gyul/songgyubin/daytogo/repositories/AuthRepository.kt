package gyul.songgyubin.daytogo.repositories

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import durdinapps.rxfirebase2.RxFirebaseAuth
import gyul.songgyubin.daytogo.models.User
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AuthRepository(context: Context) {

    fun firebaseLogin(
        auth: FirebaseAuth,
        inputEmail: String,
        inputPassword: String
    ): Maybe<User>{
        return RxFirebaseAuth.signInWithEmailAndPassword(
            auth,
            inputEmail,
            inputPassword
        ).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map { authResult->
                val firebaseUser = authResult.user!!
                val user = User(uid = firebaseUser.uid, email = firebaseUser.email!!)
                user
            }.observeOn(AndroidSchedulers.mainThread())
    }

}