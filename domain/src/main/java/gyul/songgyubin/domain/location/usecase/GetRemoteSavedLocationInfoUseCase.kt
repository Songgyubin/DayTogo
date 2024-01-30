package gyul.songgyubin.domain.usecase

import gyul.songgyubin.domain.model.LocationInfoEntity
import gyul.songgyubin.domain.repository.LocationRepository
import io.reactivex.Maybe

/**
 * 파이어베이스 DB에서 유저가 저장한 장소 리스트 가져오기
 */
class GetRemoteSavedLocationInfoUseCase(private val repository: LocationRepository) {

    operator fun invoke(): Maybe<List<LocationInfoEntity>> = repository.getSavedLocationList()

}