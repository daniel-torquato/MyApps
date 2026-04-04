package xyz.torquato.myapps.ui

import android.media.MediaRecorder
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import xyz.torquato.myapps.utils.KeyboardUtils.toKeyBoard

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalTestApi::class)
class MixerInstrumentedTest {

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    private var mediaRecorder: MediaRecorder? = null
    private var testFileName: String = ""

    @Before
    fun setup() {
        testFileName = "${appContext.externalCacheDir?.absolutePath}/test_audio.3gp"
    }

    @After
    fun tearDown() {
        mediaRecorder?.release()
        mediaRecorder = null
    }

    @Test
    fun when_given_then_2() {
        // Given
        /* open using activity rule */
        device.executeShellCommand("am start xyz.torquato.myapps/.MenuActivity")

        val button = device.findObject(By.text("Mixer"))

        Assert.assertNotNull(button)

        button?.click()

        val tokenSelector = By.text("Token")
        val messageSelector = By.text("Message")

        val tokenBar = device.findObject(tokenSelector)

        Assert.assertNotNull(tokenBar)

        tokenBar?.click()
        device.pressKeyCodes("48443d0bb0d21109c89a100b5ce2c208".toKeyBoard())


        val messageBar = device.findObject(messageSelector)

        Assert.assertNotNull(messageBar)

        messageBar?.click()
        device.pressKeyCodes("663cea190ffb83d89593f3f476b6bc24d7e679107ea26adb8caf6652d0656136".toKeyBoard())

        Assert.assertTrue(device.wait(
            Until.hasObject(By.text("01cfb6f98add6a0ea7c631de020225cc8b")),
            1_000L
        ))
    }

    @Test
    fun when_given_then_audio() {
        // 1. Initialize and configure MediaRecorder

        // 2. Wait for some time to record audio
        device.executeShellCommand("am start xyz.torquato.myapps/.MenuActivity")


        val button = device.findObject(By.text("Mixer"))

        Assert.assertNotNull(button)

        button?.click()

        val noteSelector = By.text("C#")

        val noteButton = device.findObject(noteSelector)

        Assert.assertNotNull(noteButton)

        noteButton?.click(500L)

    }

}

