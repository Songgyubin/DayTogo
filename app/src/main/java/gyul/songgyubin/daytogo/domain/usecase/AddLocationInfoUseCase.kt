package gyul.songgyubin.daytogo.domain.usecase

import com.google.firebase.database.DatabaseReference
import gyul.songgyubin.daytogo.domain.model.LocationInfo
import gyul.songgyubin.daytogo.domain.repository.LocationRepository
import io.reactivex.Completable

class AddLocationInfoUseCase(private val repository: LocationRepository) {

    operator fun invoke(
        dbReference: DatabaseReference,
        uid: String,
        locationInfo: LocationInfo
    ):Completable = repository.saveLocationDB(dbReference, uid, locationInfo)

}