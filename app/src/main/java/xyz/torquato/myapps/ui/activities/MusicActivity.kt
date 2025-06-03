package xyz.torquato.myapps.ui.activities

import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import xyz.torquato.myapps.api.sound.ISoundRepository
import xyz.torquato.myapps.data.track.datasource.TrackDatabase
import xyz.torquato.myapps.data.waves.SoundRepository
import xyz.torquato.myapps.ui.theme.MyAppsTheme
import xyz.torquato.myapps.ui.views.FrequencySelector
import xyz.torquato.myapps.ui.views.MixerViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MusicActivity : ComponentActivity() {

    @Inject
    lateinit var soundRepository: ISoundRepository

    @Inject
    lateinit var viewMode: MixerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("MyTag: Music Mixer Created")
        soundRepository.start()
        setContent {
            MyAppsTheme {
                FrequencySelector(viewModel = viewMode, onTouch = ::onTouchEvent)
            }
        }
    }

    private fun onTouchEvent(action: Int, frequency: Float, amplitude: Float) {
        println("MyTag: action=$action, frequency=$frequency, amplitude=$amplitude")
        soundRepository.setTouchEvent(action, frequency, amplitude)
    }

    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        println("MyTag Back Pressed")
        return super.getOnBackInvokedDispatcher()
    }

    override fun onDestroy() {
        soundRepository.destroy()
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