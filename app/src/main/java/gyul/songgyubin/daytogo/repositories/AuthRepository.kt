package gyul.songgyubin.daytogo.repositories

import android.content.Context
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import durdinapps.rxfirebase2.RxFirebaseAuth
import gyul.songgyubin.daytogo.models.User
import io.reactivex.Maybe

class AuthRepository(context: Context) {

    fun firebaseLogin(
        auth: FirebaseAuth,
        inputEmail: String,
        inputPassword: String
    ): Maybe<AuthResult> {
        return RxFirebaseAuth.signInWithEmailAndPassword(
            auth,
            inputEmail,
            inputPassword
        )
    }
}