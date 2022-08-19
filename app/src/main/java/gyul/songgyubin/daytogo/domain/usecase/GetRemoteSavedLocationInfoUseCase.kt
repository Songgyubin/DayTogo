package gyul.songgyubin.daytogo.domain.usecase

import gyul.songgyubin.daytogo.domain.model.LocationInfo
import gyul.songgyubin.daytogo.domain.repository.LocationRepository
import io.reactivex.Maybe

class GetRemoteSavedLocationInfoUseCase(private val repository: LocationRepository) {

    operator fun invoke(): Maybe<List<LocationInfo>> = repository.getSavedLocationList()

}