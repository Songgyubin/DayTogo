package gyul.songgyubin.domain.repository

import gyul.songgyubin.domain.auth.model.UserEntity
import io.reactivex.Completable
import io.reactivex.Maybe

/**
 * UseCase에 필요한 Interface 선언
 * RepositoryImpl에서 구현된다.
 * 회원가입 및 로그인 관련
 */

interface AuthRepository {
    fun firebaseLogin(
        inputEmail: String,
        inputPassword: String
    ): Maybe<UserEntity>

    fun createUser(
        inputEmail: String,
        inputPassword: String
    ): Maybe<UserEntity>

    fun createUserInfoDB(
        userEntity: UserEntity
    ): Completable
}