package xyz.torquato.myapps.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mywaves.ui.views.FrequencySelector
import xyz.torquato.myapps.data.waves.SoundRepository
import xyz.torquato.myapps.ui.theme.MyAppsTheme

class MusicActivity : ComponentActivity() {
    private val soundRepository = SoundRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        soundRepository.start()
        enableEdgeToEdge()
        setContent {
            MyAppsTheme {
                FrequencySelector(soundRepository::setTouchEvent)
            }
        }
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
        FrequencySelector {_, _, _ -> }
    }
}