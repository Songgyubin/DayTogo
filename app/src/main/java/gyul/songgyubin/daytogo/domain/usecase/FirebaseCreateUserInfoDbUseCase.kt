package gyul.songgyubin.daytogo.domain.usecase

import gyul.songgyubin.daytogo.domain.model.User
import gyul.songgyubin.daytogo.domain.repository.AuthRepository
import io.reactivex.Completable

class FirebaseCreateUserInfoDbUseCase(private val repository: AuthRepository) {
    operator fun invoke(
        user: User
    ): Completable = repository.createUserInfoDB(user)
}