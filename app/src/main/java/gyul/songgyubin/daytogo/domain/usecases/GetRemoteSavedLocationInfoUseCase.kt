package gyul.songgyubin.daytogo.domain.usecases

import com.google.firebase.database.DatabaseReference
import gyul.songgyubin.daytogo.data.mapper.LocationInfoMapper
import gyul.songgyubin.daytogo.domain.models.LocationInfo
import gyul.songgyubin.daytogo.domain.repositories.LocationRepository
import io.reactivex.Maybe

class GetRemoteSavedLocationInfoUseCase(private val repository: LocationRepository) {

    operator fun invoke(
        dbReference: DatabaseReference,
        uid: String
    ): Maybe<List<LocationInfo>> = repository.getSavedLocationList(dbReference, uid)

}