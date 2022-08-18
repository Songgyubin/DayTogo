package gyul.songgyubin.daytogo.domain.usecases

import com.google.firebase.auth.FirebaseAuth
import gyul.songgyubin.daytogo.domain.models.User
import gyul.songgyubin.daytogo.domain.repositories.AuthRepository
import io.reactivex.Maybe

class FirebaseCreateUserUseCase(private val repository: AuthRepository) {
    operator fun invoke(auth: FirebaseAuth, email: String, password: String): Maybe<User> =
        repository.createUser(auth, email, password)
}