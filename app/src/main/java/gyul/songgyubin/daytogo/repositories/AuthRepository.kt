package gyul.songgyubin.daytogo.repositories

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import durdinapps.rxfirebase2.RxFirebaseAuth
import io.reactivex.Maybe

class AuthRepository {
    private val auth: FirebaseAuth by lazy { Firebase.auth }

    fun firebaseLogin(inputEmail: String, inputPassword: String): Maybe<AuthResult> {
        return RxFirebaseAuth.signInWithEmailAndPassword(
            auth,
            inputEmail,
            inputPassword
        )
    }
}