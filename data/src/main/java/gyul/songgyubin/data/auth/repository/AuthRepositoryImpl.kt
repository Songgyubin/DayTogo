package gyul.songgyubin.data.auth.repository

import gyul.songgyubin.data.auth.model.UserMapper.toEntity
import gyul.songgyubin.data.auth.source.AuthDataSource
import gyul.songgyubin.domain.auth.model.UserEntity
import gyul.songgyubin.domain.repository.AuthRepository
import javax.inject.Inject


class AuthRepositoryImpl
@Inject
constructor(private val authDataSource: AuthDataSource) : AuthRepository {

    /**
     * 파이어베이스 로그인
     */
    override suspend fun firebaseLogin(
        email: String,
        password: String
    ): UserEntity {
        return authDataSource.firebaseLogin(email, password)
            .toEntity()
    }

    /**
     * 유저 생성
     */
    override suspend fun createUser(
        email: String,
        password: String
    ): UserEntity {
        return authDataSource.createUser(email, password)
            .toEntity()
    }

    /**
     * 유저 정보 firebase DB에 저장
     */
    override suspend fun saveUserInfoDB(
        user: UserEntity
    ): Result<Unit> {
        return authDataSource.saveUserInfoDB(user)
    }
}