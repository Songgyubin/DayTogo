package gyul.songgyubin.daytogo.domain.usecase

import gyul.songgyubin.daytogo.domain.model.LocationInfo
import gyul.songgyubin.daytogo.domain.repository.LocationRepository
import io.reactivex.Completable

class AddLocationInfoUseCase(private val repository: LocationRepository) {

    operator fun invoke(
        locationInfo: LocationInfo
    ):Completable = repository.saveLocationDB(locationInfo)

}