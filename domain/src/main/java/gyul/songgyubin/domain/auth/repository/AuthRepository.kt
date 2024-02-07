package gyul.songgyubin.domain.repository

import gyul.songgyubin.domain.auth.model.UserEntity
import gyul.songgyubin.domain.auth.model.UserRequest

/**
 * UseCase에 필요한 Interface 선언
 * RepositoryImpl에서 구현된다.
 * 회원가입 및 로그인 관련
 */

interface AuthRepository {
    /**
     * 파이어베이스 로그인
     */
    suspend fun firebaseLogin(
        email: String,
        password: String
    ): UserEntity

    /**
     * User 생성
     */
    suspend fun createUser(
        email: String,
        password: String
    ): UserEntity

    /**
     * User 정보 Firebase DB에 저장
     */
    suspend fun saveUserInfoDB(
        user: UserRequest
    ): Result<Unit>
}