package gyul.songgyubin.domain.usecase

import gyul.songgyubin.domain.model.LocationInfoEntity
import gyul.songgyubin.domain.repository.LocationRepository
import io.reactivex.Completable

/**
 * 사용자가 저장한 장소를 DB에 저장
 * DB 내 'locationList' 리스트에 push
 */

class AddLocationInfoUseCase(private val repository: LocationRepository) {

    operator fun invoke(
        locationInfoEntity: LocationInfoEntity
    ):Completable = repository.saveLocationDB(locationInfoEntity)

}