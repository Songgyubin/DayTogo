package gyul.songgyubin.domain.auth.usecase

import gyul.songgyubin.domain.auth.model.UserEntity
import gyul.songgyubin.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * 이메일과 패스워드만 사용하여 파이어베이스 회원가입 UseCase
 */
class FirebaseCreateUserUseCase
@Inject
constructor(private val repository: AuthRepository) {
    operator fun invoke(email: String, password: String): Flow<UserEntity> = flow {
        val item = repository.createUser(email, password)
        emit(item)
    }.flowOn(Dispatchers.IO)
}