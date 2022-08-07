package gyul.songgyubin.daytogo.repositories

import android.content.Context
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import durdinapps.rxfirebase2.RxFirebaseAuth
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