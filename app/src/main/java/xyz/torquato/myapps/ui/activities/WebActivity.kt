package xyz.torquato.myapps.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import xyz.torquato.myapps.ui.component.web.view.WebComponent
import xyz.torquato.myapps.ui.theme.MyAppsTheme

@AndroidEntryPoint
class WebActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("MyTag: Music Mixer Created")
        setContent {
            MyAppsTheme {
                WebComponent()
            }
        }
    }
}