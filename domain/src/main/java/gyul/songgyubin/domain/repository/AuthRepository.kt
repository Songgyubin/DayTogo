package gyul.songgyubin.domain.repository

import gyul.songgyubin.domain.model.User
import io.reactivex.Completable
import io.reactivex.Maybe

interface AuthRepository {
    fun firebaseLogin(
        inputEmail: String,
        inputPassword: String
    ): Maybe<User>

    fun createUser(
        inputEmail: String,
        inputPassword: String
    ): Maybe<User>

    fun createUserInfoDB(
        user: User
    ): Completable
}