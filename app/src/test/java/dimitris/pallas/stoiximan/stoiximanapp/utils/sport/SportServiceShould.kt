package dimitris.pallas.stoiximan.stoiximanapp.utils.sport

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dimitris.pallas.stoiximan.mvp.utils.BaseUnitTest
import dimitris.pallas.stoiximan.stoiximanapp.SportAPI
import dimitris.pallas.stoiximan.stoiximanapp.SportService
import dimitris.pallas.stoiximan.stoiximanapp.SportsModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import java.lang.RuntimeException

class SportServiceShould : BaseUnitTest() {
    private lateinit var service: SportService
    private val api: SportAPI = mock()
    private val sports: List<SportsModel> = mock()

    @Test
    fun fetchSportsFromAPI() = runBlockingTest {
        mockSuccessfulCase()
        verify(api, times(1)).fetchSport()
    }


    @Test
    fun convertValuesToFlowResultAndEmitThem() = runBlockingTest {
        whenever(api.fetchSport()).thenReturn(sports)
        service = SportService(api)
        Assert.assertEquals(Result.success(sports), service.fetchSport().first())
    }

    @Test
    fun emitErrorResultWhenNetworkFails() = runBlockingTest {
        mockErrorCase()
        Assert.assertEquals("Something went wrong", service.fetchSport().first().exceptionOrNull()?.message)
    }

    private suspend fun mockErrorCase() {
        whenever(api.fetchSport()).thenThrow(RuntimeException("Damn backend developers"))
        service = SportService(api)
    }

    private suspend fun mockSuccessfulCase() {
        service = SportService(api)
        service.fetchSport().first()
    }
}