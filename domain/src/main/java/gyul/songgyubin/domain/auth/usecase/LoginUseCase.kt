package gyul.songgyubin.domain.auth.usecase

import gyul.songgyubin.domain.auth.model.UserEntity
import gyul.songgyubin.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * 파이어베이스 로그인 UseCase
 */
class LoginUseCase
@Inject
constructor(private val repository: AuthRepository) {
    operator fun invoke(
        inputEmail: String,
        inputPassword: String
    ): Flow<UserEntity> = flow {
        val item = repository.firebaseLogin(inputEmail, inputPassword)
        emit(item)
    }.flowOn(Dispatchers.IO)
}