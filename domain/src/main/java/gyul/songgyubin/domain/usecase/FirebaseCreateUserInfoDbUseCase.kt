package gyul.songgyubin.domain.usecase

import gyul.songgyubin.domain.model.User
import gyul.songgyubin.domain.repository.AuthRepository
import io.reactivex.Completable

class FirebaseCreateUserInfoDbUseCase(private val repository: AuthRepository) {
    operator fun invoke(
        user: User
    ): Completable = repository.createUserInfoDB(user)
}