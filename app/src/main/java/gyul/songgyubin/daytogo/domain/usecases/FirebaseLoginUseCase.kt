package gyul.songgyubin.daytogo.domain.usecases

import com.google.firebase.auth.FirebaseAuth
import gyul.songgyubin.daytogo.domain.models.User
import gyul.songgyubin.daytogo.domain.repositories.AuthRepository
import io.reactivex.Maybe

class FirebaseLoginUseCase(private val repository: AuthRepository) {
    operator fun invoke(
        auth: FirebaseAuth,
        inputEmail: String,
        inputPassword: String
    ): Maybe<User> = repository.firebaseLogin(auth, inputEmail, inputPassword)
}