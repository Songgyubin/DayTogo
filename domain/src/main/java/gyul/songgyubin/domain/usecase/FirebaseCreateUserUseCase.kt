package gyul.songgyubin.domain.usecase

import gyul.songgyubin.domain.model.User
import gyul.songgyubin.domain.repository.AuthRepository
import io.reactivex.Maybe

/**
 * 이메일과 패스워드만 사용하여 파이어베이스 회원가입
 */
class FirebaseCreateUserUseCase(private val repository: AuthRepository) {
    operator fun invoke(email: String, password: String): Maybe<User> =
        repository.createUser(email, password)
}