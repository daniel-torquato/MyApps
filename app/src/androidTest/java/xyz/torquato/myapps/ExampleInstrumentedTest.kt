package xyz.torquato.myapps

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import xyz.torquato.myapps.data.cypher.CypherRepository

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    val repository = CypherRepository()

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("xyz.torquato.myapps", appContext.packageName)
    }

    @Test
    fun when_given_then() {
        // Context of the app under test.
        repository.setToken("48443d0bb0d21109c89a100b5ce2c208")
        repository.setMessage("663cea190ffb83d89593f3f476b6bc24d7e679107ea26adb8caf6652d0656136")

        val result = repository.message.value

        assertEquals("01cfb6f98add6a0ea7c631de020225cc8b", result)
    }

    @Test
    fun when_given_then_3() {
        val input1 = "08c2"
        val input2 = "3661"

        val result = CypherTester.sum(input1, input2)

        assertEquals("3f23", result)
    }

    @Test
    fun when_given_then_3_0() {
        val input1 = "08c2"
        val input2 = "f73d"

        val result = CypherTester.sum(input1, input2)

        assertEquals("ffff", result)
    }

    @Test
    fun when_given_then_3_1() {
        val input1 = "08c2"

        val result = CypherTester.neg(input1)

        assertEquals("f73e", result)
    }

    @Test
    fun when_given_then_4() {
        val input1 = "c208"

        val result = CypherTester.converter(input1)

        assertEquals("c208", result)
    }


    @Test
    fun when_given_then_6() {
        val input1 = "08c2"
        val input2 = "3661"

        val result = CypherTester.times(input1, input2)

        assertEquals("01dc3d82", result)
    }

    @Test
    fun when_given_then_7() {
        val input1 = "08c2"
        val input2 = "3661"

        val result = CypherTester.mod(input2, input1)

        assertEquals("01d5", result)
    }


}

