package xyz.torquato.myapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
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
import androidx.room.Room
import kotlinx.serialization.Serializable
import xyz.torquato.myapps.data.track.datasource.TrackDatabase
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
  //  Column(
  //      modifier = Modifier.fillMaxSize().padding(30.dp),
  //      horizontalAlignment = Alignment.CenterHorizontally
  //  ) {
  //      Row(
  //          modifier = Modifier.fillMaxSize(),
  //          verticalAlignment = Alignment.CenterVertically
  //      ) {
  //          Button(onClick = onGameSelected, shape = RectangleShape) {
  //              Text("Open Game", color = Color.Red)
  //          }
  //          Button(onClick = onMixerSelected, shape = RectangleShape) {
  //              Text("Frequency Mixer", color = Color.Blue)
  //          }
//
  //      }
  //  }


    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement =  Arrangement.Center,
        contentPadding = PaddingValues(50.dp),
    ) {
        item {
            Button(
                onClick = onGameSelected,
                modifier = Modifier.size(100.dp),
                contentPadding = PaddingValues(10.dp),
                elevation = ButtonDefaults.buttonElevation(10.dp),
                shape = RoundedCornerShape(20)
            ) {
                Text("Game", color = Color.Red)
            }
        }
        item {
            Button(
                onClick = onMixerSelected,
                modifier = Modifier.size(100.dp),
                contentPadding = PaddingValues(10.dp),
                elevation = ButtonDefaults.buttonElevation(10.dp),
                shape = RoundedCornerShape(20)
            ) {
                Text("Mixer", color = Color.Blue)
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