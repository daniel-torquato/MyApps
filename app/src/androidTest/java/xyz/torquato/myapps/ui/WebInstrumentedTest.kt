package xyz.torquato.myapps.ui

import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalTestApi::class)
class WebInstrumentedTest {



    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())



    @Test
    fun open_clock() {
        // Given
        /* open using activity rule */
        device.pressHome()

        val appUT = device.findObject(By.text("Clock"))

        appUT.clickAndWait(Until.newWindow(), 3000)
    }

    @Test
    fun when_given_then_2() {
        // Given
        /* open using activity rule */
        device.executeShellCommand("am start xyz.torquato.myapps/.MenuActivity")

        val webButton = device.findObject(By.text("Web"))

        webButton?.click()

        device.wait(Until.hasObject(By.text("Search")), 1_000L)

        val searchBar = device.findObject(By.text("Search"))

        searchBar?.click()
        device.pressKeyCodes(
            intArrayOf(
                NativeKeyEvent.KEYCODE_E,
                NativeKeyEvent.KEYCODE_X,
                NativeKeyEvent.KEYCODE_A,
                NativeKeyEvent.KEYCODE_M,
                NativeKeyEvent.KEYCODE_P,
                NativeKeyEvent.KEYCODE_L,
                NativeKeyEvent.KEYCODE_E,
                NativeKeyEvent.KEYCODE_ENTER
            )
        )

        device.wait(Until.findObject(By.res("LOADED_IMAGE")), 2_000)
    }

}

