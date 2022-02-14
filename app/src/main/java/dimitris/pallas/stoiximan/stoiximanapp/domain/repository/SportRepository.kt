package dimitris.pallas.stoiximan.stoiximanapp.domain.repository

import dimitris.pallas.stoiximan.stoiximanapp.SportService
import dimitris.pallas.stoiximan.stoiximanapp.SportsModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SportRepository @Inject constructor(
    private val service: SportService
) {
    suspend fun getSport(): Flow<Result<List<SportsModel>>> =
        service.fetchSport()

}