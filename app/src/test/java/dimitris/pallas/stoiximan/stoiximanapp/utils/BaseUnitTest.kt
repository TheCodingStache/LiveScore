package dimitris.pallas.stoiximan.mvp.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

open class BaseUnitTest {
    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutionRule =
        InstantTaskExecutorRule() // allows execution of livedata to happen instantly
}