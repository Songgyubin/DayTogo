package gyul.songgyubin.domain.location.usecase

import gyul.songgyubin.domain.location.model.LocationEntity
import gyul.songgyubin.domain.location.repository.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * 파이어베이스 DB에서 유저가 저장한 장소 리스트 가져오는 UseCase
 */
class GetRemoteSavedLocationInfoUseCase
@Inject
constructor(private val repository: LocationRepository) {
    operator fun invoke(): Flow<List<LocationEntity>> = flow {
        val item = repository.getSavedLocationList()
        emit(item)
    }.flowOn(Dispatchers.IO)
}