package xyz.torquato.myapps

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class CypherInstrumentedTest {

    @Test
    fun when_given_then_3() {
        val input1 = "c208"
        val input2 = "6136"

        val result = CypherTester.sum(input1, input2)

        assertEquals("3f23", result)
    }

    @Test
    fun when_given_then_4() {
        val input1 = "c208"
        val input2 = "6136"

        val result = CypherTester.sum(input1, input2)

        assertEquals("3f23", result)
    }


}

