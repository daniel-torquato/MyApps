package xyz.torquato.myapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mywaves.ui.views.FrequencySelector
import xyz.torquato.myapps.ui.theme.MyAppsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppsTheme {
                FrequencySelector()
            }
        }
    }

    private external fun entry(): String

    companion object {
        // Used to load the 'cpp' library on application startup.
        init {
            System.loadLibrary("myapps")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyAppsTheme {
        FrequencySelector {_, _, _ -> }
    }
}