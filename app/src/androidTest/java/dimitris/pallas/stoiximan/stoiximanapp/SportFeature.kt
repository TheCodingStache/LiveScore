package dimitris.pallas.stoiximan.stoiximanapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import dimitris.pallas.stoiximan.stoiximanapp.di.idlingResource
import dimitris.pallas.stoiximan.stoiximanapp.presentation.MainActivity
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test

class SportFeature : BaseUITest() {
    val mActivityRule = ActivityTestRule(MainActivity::class.java)
        @Rule get

    @Test
    fun displayScreenTitle() {
        assertDisplayed("Stoiximan MVP")
    }

    @Test
    fun displayListOfSports() {
        assertRecyclerViewItemCount(R.id.parent_recycler_view, 11)
        onView(
            CoreMatchers.allOf(
                withId(R.id.sport),
                isDescendantOfA(nthChildOf(withId(R.id.parent_recycler_view), 0))
            )
        ).check(ViewAssertions.matches(withText("FOOTBALL")))
            .check(
                ViewAssertions.matches(isDisplayed())
            )
        onView(
            CoreMatchers.allOf(
                withId(R.id.child_recycler_view),
                isDescendantOfA(nthChildOf(withId(R.id.parent_recycler_view), 0))
            )
        ).check(
            ViewAssertions.matches(isDisplayed())
        )
    }

    @Test
    fun displayLoaderWhileFetchingSports() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        assertDisplayed(R.id.loader)
    }

    @Test
    fun hidesLoader() {
        assertNotDisplayed(R.id.loader)
    }
}