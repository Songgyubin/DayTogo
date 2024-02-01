package gyul.songgyubin.domain.auth.usecase

import gyul.songgyubin.domain.auth.model.UserEntity
import gyul.songgyubin.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * 회원가입 시 유저 정보를 담는 DB 저장 UseCase
 */
class SaveUserInfoDbUseCase
@Inject
constructor(private val repository: AuthRepository) {
    operator fun invoke(
        userEntity: UserEntity
    ): Flow<Result<Unit>> = flow {
        val item = repository.saveUserInfoDB(userEntity)
        emit(item)
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}