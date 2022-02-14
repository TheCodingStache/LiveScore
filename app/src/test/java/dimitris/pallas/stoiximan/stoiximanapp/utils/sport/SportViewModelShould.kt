package dimitris.pallas.stoiximan.stoiximanapp.utils.sport

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dimitris.pallas.stoiximan.mvp.utils.BaseUnitTest
import dimitris.pallas.stoiximan.mvp.utils.captureValues
import dimitris.pallas.stoiximan.mvp.utils.getValueForTest
import dimitris.pallas.stoiximan.stoiximanapp.domain.repository.SportRepository
import dimitris.pallas.stoiximan.stoiximanapp.presentation.SportViewModel
import dimitris.pallas.stoiximan.stoiximanapp.SportsModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class SportViewModelShould : BaseUnitTest(){
    private val repository: SportRepository = mock()
    private val sport = mock<List<SportsModel>>()
    private val expected = Result.success(sport)
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getSportFromRepository() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.sports.getValueForTest()
        verify(repository, times(1)).getSport() //forcing livedata to complete the operations
        // they need in order to get and emit a value
    }
    @Test
    fun emitsSportsFromRepository() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        assertEquals(expected, viewModel.sports.getValueForTest())
    }
    @Test
    fun emitErrorWhenReceiveError() {
        val viewModel = mockErrorCase()
        assertEquals(exception, viewModel.sports.getValueForTest()!!.exceptionOrNull())
    }
    @Test
    fun showSpinnerWhileLoading() = runBlockingTest {
        val viewModel: SportViewModel = mockSuccessfulCase()
        viewModel.loader.captureValues {
            viewModel.sports.getValueForTest()
            assertEquals(true, values[0])
        }
    }
    @Test
    fun closeLoaderAfterError() = runBlockingTest{
        val viewModel : SportViewModel = mockErrorCase()
        viewModel.loader.captureValues {
            viewModel.sports.getValueForTest()
            assertEquals(false,values.last())
        }
    }
    private fun mockSuccessfulCase(): SportViewModel {
        runBlocking {
            whenever(repository.getSport()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        return SportViewModel(repository)
    }

    private fun mockErrorCase(): SportViewModel {
        runBlocking {
            whenever(repository.getSport()).thenReturn(
                flow {
                    emit(kotlin.Result.failure<List<SportsModel>>(exception))
                }
            )
        }
        return SportViewModel(repository)
    }


}