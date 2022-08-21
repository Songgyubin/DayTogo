package gyul.songgyubin.domain.usecase

import gyul.songgyubin.domain.model.User
import gyul.songgyubin.domain.repository.AuthRepository
import io.reactivex.Maybe

class FirebaseCreateUserUseCase(private val repository: AuthRepository) {
    operator fun invoke(email: String, password: String): Maybe<User> =
        repository.createUser(email, password)
}