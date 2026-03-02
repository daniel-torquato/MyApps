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
    fun when_given_then_sum() {
        val input1 = "c208"
        val input2 = "6136"

        val result = CypherTester.sum(input1, input2)

        assertEquals("01233e", result)
    }

    @Test
    fun when_given_then_input() {
        val input1 = "c208"
        val input2 = "6136"

        val result = CypherTester.sum(input1, input2)

        assertEquals("01233e", result)
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

    @Test
    fun when_given_then_input_2() {
        val input2 = "3f62"

        val result = CypherTester.input(input2)

        assertEquals("00003f62", result)
    }


}

