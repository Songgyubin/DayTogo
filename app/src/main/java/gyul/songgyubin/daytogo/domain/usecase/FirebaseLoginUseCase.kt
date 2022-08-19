package gyul.songgyubin.daytogo.domain.usecase

import gyul.songgyubin.daytogo.domain.model.User
import gyul.songgyubin.daytogo.domain.repository.AuthRepository
import io.reactivex.Maybe

class FirebaseLoginUseCase(private val repository: AuthRepository) {
    operator fun invoke(
        inputEmail: String,
        inputPassword: String
    ): Maybe<User> = repository.firebaseLogin(inputEmail, inputPassword)
}