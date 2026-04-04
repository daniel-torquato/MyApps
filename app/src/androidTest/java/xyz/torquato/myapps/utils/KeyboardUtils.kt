package xyz.torquato.myapps.utils

import android.view.KeyEvent

object KeyboardUtils {

    fun String.toKeyBoard(endWithEnter: Boolean = false): IntArray {
        val converted = map {
            KeyEvent.keyCodeFromString("KEYCODE_${it.uppercase()}")
        }

        return (if (endWithEnter) converted + KeyEvent.KEYCODE_ENTER else converted).toIntArray()
    }
}