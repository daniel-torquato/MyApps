package xyz.torquato.myapps.web

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import xyz.torquato.myapps.MenuActivity

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalTestApi::class)
class WebInstrumentedTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule
    val activityRule = ActivityScenarioRule(MenuActivity::class.java)

    @Test
    fun when_open_web_query_given_open_web_search_then_query_bar_is_visible() {
        // Given
        /* open using activity rule */

        // When
        composeTestRule.clickButtonWithTag("BUTTON_Web")

        composeTestRule.waitUntilAtLeastOneExists(hasTestTag("SEARCH"), 5_000L)

        // Then
        composeTestRule.onNodeWithTag("QUERY_BAR").assertIsDisplayed()
    }

    private fun AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>.clickButtonWithTag(
        tag: String,
        timeoutMillis: Long = 5_00L
    ) {
        waitUntil(timeoutMillis, {
            onNodeWithTag(tag).isDisplayed()
        })

        onNodeWithTag(tag).performClick()
    }


}

