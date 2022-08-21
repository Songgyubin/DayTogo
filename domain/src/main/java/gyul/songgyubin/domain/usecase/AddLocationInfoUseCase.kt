package gyul.songgyubin.domain.usecase

import gyul.songgyubin.domain.model.LocationInfo
import gyul.songgyubin.domain.repository.LocationRepository
import io.reactivex.Completable

class AddLocationInfoUseCase(private val repository: LocationRepository) {

    operator fun invoke(
        locationInfo: LocationInfo
    ):Completable = repository.saveLocationDB(locationInfo)

}