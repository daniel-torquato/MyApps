package xyz.torquato.myapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.activity
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import kotlinx.serialization.Serializable
import xyz.torquato.myapps.ui.activities.EngineActivity
import xyz.torquato.myapps.ui.activities.MusicActivity
import xyz.torquato.myapps.ui.theme.MyAppsTheme
import xyz.torquato.myapps.ui.views.FrequencySelector

sealed interface ScreenRoute {

    @Serializable
    data object None : ScreenRoute

    @Serializable
    data object Menu : ScreenRoute

    @Serializable
    data object Home : ScreenRoute

    @Serializable
    data object FrequencySelector : ScreenRoute

    @Serializable
    data object Engine: ScreenRoute
}


class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppsTheme {
                val navController = rememberNavController()
                SetupNavigator(navController)
                Menu(
                    onMixerSelected = { navController.navigate(ScreenRoute.Home) },
                    onGameSelected = { navController.navigate(ScreenRoute.Engine) }
                )
            }
        }
    }
}

@Composable
fun SetupNavigator(
    navController: NavController
) {
    navController.graph = navController.createGraph(
        startDestination = ScreenRoute.None
    ) {
        activity<ScreenRoute.Home> {
            label = "Music"
            activityClass = MusicActivity::class
        }

        activity<ScreenRoute.FrequencySelector> {
            label = "Music"
            activityClass = MusicActivity::class
        }

        activity<ScreenRoute.Engine> {
            label = "Game"
            activityClass = EngineActivity::class
        }

        composable<ScreenRoute.Menu> {
            Menu(
                onMixerSelected = { navController.navigate(ScreenRoute.FrequencySelector) },
                onGameSelected = { navController.navigate(ScreenRoute.Engine) }
            )
        }

        composable<ScreenRoute.None> { }
    }
}

@Composable
fun Menu(
    onMixerSelected: () -> Unit,
    onGameSelected: () -> Unit
) {
    Column(
        modifier = Modifier.padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onGameSelected, shape = RectangleShape) {
                Text("Open Game", color = Color.Red)
            }
            Button(onClick = onMixerSelected, shape = RectangleShape) {
                Text("Frequency Mixer", color = Color.Blue)
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyAppsTheme {
        FrequencySelector { _, _, _ -> }
    }
}