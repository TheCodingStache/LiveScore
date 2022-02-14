package dimitris.pallas.stoiximan.stoiximanapp


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SportService @Inject constructor(
    private val api: SportAPI
) {
    suspend fun fetchSport(): Flow<Result<List<SportsModel>>> {
        return flow {
            emit(Result.success(api.fetchSport()))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }
}