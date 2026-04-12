package xyz.torquato.myapps.ui.mixer

import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import xyz.torquato.myapps.presentation.viewmodel.music.MusicController
import xyz.torquato.myapps.ui.theme.MyAppsTheme
import javax.inject.Inject

@AndroidEntryPoint
class MusicActivity : ComponentActivity() {

    @Inject
    lateinit var controller: MusicController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("MyTag: Music Mixer Created")

        setContent {
            MyAppsTheme {
                FrequencySelector()
            }
        }
    }

    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        println("MyTag Back Pressed")
        return super.getOnBackInvokedDispatcher()
    }

    override fun onStart() {
        super.onStart()
        controller.enabledChannel(true)
    }

    override fun onDestroy() {
        controller.enabledChannel(false)
        super.onDestroy()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyAppsTheme {
        //FrequencySelector { _, _, _ -> }
    }
}