package gyul.songgyubin.domain.location.usecase

import gyul.songgyubin.domain.location.model.LocationRequest
import gyul.songgyubin.domain.location.repository.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * 사용자가 저장한 장소를 DB에 저장하는 UseCase
 * DB 내 'locationList' 리스트에 push
 */
class AddLocationInfoUseCase
@Inject
constructor(private val repository: LocationRepository) {

    operator fun invoke(
        locationRequest: LocationRequest
    ): Flow<Result<Unit>> = flow {
        val item = repository.saveLocationDB(locationRequest)
        emit(item)
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}