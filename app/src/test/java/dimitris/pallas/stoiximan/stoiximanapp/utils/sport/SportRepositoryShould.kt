package dimitris.pallas.stoiximan.stoiximanapp.utils.sport

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dimitris.pallas.stoiximan.mvp.utils.BaseUnitTest
import dimitris.pallas.stoiximan.stoiximanapp.domain.repository.SportRepository
import dimitris.pallas.stoiximan.stoiximanapp.SportService
import dimitris.pallas.stoiximan.stoiximanapp.SportsModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

class SportRepositoryShould : BaseUnitTest() {
    private val service: SportService = mock()
    private val sports = mock<List<SportsModel>>()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getSportsFromService() = runBlockingTest {
        val repository = SportRepository(service)
        repository.getSport()
        verify(service, times(1)).fetchSport()
    }

    @Test
    fun emitsSportsFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()
        Assert.assertEquals(sports, repository.getSport().first().getOrNull())
    }

    @Test
    fun propagateErrors() = runBlockingTest {
        val repository = mockFailCase()
        Assert.assertEquals(exception, repository.getSport().first().exceptionOrNull())
    }

    private suspend fun mockSuccessfulCase(): SportRepository {
        whenever(service.fetchSport()).thenReturn(
            flow {
                emit(Result.success(sports))
            }
        )
        return SportRepository(service)
    }

    private suspend fun mockFailCase(): SportRepository {
        whenever(service.fetchSport()).thenReturn(
            flow {
                emit(Result.failure<List<SportsModel>>(exception))
            }
        )
        return SportRepository(service)
    }
}