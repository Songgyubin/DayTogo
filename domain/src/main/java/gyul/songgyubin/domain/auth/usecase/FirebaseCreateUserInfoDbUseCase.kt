package gyul.songgyubin.domain.usecase

import gyul.songgyubin.domain.auth.model.UserEntity
import gyul.songgyubin.domain.repository.AuthRepository
import io.reactivex.Completable

/**
 * 회원가입 시 유저 정보를 담는 DB 생성
 * 유저 정보: uid, email
 */
class FirebaseCreateUserInfoDbUseCase(private val repository: AuthRepository) {
    operator fun invoke(
        userEntity: UserEntity
    ): Completable = repository.createUserInfoDB(userEntity)
}