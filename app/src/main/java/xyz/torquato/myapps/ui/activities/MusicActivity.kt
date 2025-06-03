package xyz.torquato.myapps.ui.activities

import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import xyz.torquato.myapps.data.track.datasource.TrackDatabase
import xyz.torquato.myapps.data.waves.SoundRepository
import xyz.torquato.myapps.ui.theme.MyAppsTheme
import xyz.torquato.myapps.ui.views.FrequencySelector

@AndroidEntryPoint
class MusicActivity : ComponentActivity() {
    private val soundRepository = SoundRepository()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO(Add insert with complete salved points)
        // val db = Room.databaseBuilder(
        //    applicationContext,
        //    TrackDatabase::class.java, "raw-tracks"
        //).build()

        println("MyTag: Music Mixer Created")
        soundRepository.start()
        setContent {
            MyAppsTheme {
                FrequencySelector(::onTouchEvent)
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
        FrequencySelector { _, _, _ -> }
    }
}