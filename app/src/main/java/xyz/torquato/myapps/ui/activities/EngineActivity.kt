package xyz.torquato.myapps.ui.activities

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.window.OnBackInvokedDispatcher
import com.google.androidgamesdk.GameActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EngineActivity : GameActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        println("MyTag: Created")
        super.onCreate(savedInstanceState)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUi()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.keyCode == KeyEvent.KEYCODE_BACK)
            finish()
        return super.onKeyDown(keyCode, event)
    }

    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        println("MyTag: Back button on engine ")
        return super.getOnBackInvokedDispatcher()
    }

    private fun hideSystemUi() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}