package gyul.songgyubin.domain.usecase

import gyul.songgyubin.domain.model.LocationInfo
import gyul.songgyubin.domain.repository.LocationRepository
import io.reactivex.Maybe

class GetRemoteSavedLocationInfoUseCase(private val repository: LocationRepository) {

    operator fun invoke(): Maybe<List<LocationInfo>> = repository.getSavedLocationList()

}