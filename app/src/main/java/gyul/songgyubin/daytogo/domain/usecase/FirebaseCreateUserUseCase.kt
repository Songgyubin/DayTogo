package gyul.songgyubin.daytogo.domain.usecase

import gyul.songgyubin.daytogo.domain.model.User
import gyul.songgyubin.daytogo.domain.repository.AuthRepository
import io.reactivex.Maybe

class FirebaseCreateUserUseCase(private val repository: AuthRepository) {
    operator fun invoke(email: String, password: String): Maybe<User> =
        repository.createUser(email, password)
}