package gyul.songgyubin.daytogo.domain.usecases

import com.google.firebase.database.DatabaseReference
import gyul.songgyubin.daytogo.domain.models.User
import gyul.songgyubin.daytogo.domain.repositories.AuthRepository
import io.reactivex.Completable

class FirebaseCreateUserInfoDbUseCase(private val repository: AuthRepository) {
    operator fun invoke(
        dbReference: DatabaseReference,
        user: User
    ): Completable = repository.createUserInfoDB(
        dbReference, user
    )
}