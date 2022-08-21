package gyul.songgyubin.domain.usecase

import gyul.songgyubin.domain.model.User
import gyul.songgyubin.domain.repository.AuthRepository
import io.reactivex.Maybe

class FirebaseLoginUseCase(private val repository: AuthRepository) {
    operator fun invoke(
        inputEmail: String,
        inputPassword: String
    ): Maybe<User> = repository.firebaseLogin(inputEmail, inputPassword)
}