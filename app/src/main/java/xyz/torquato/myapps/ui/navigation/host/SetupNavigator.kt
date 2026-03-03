package xyz.torquato.myapps.ui.navigation.host

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import xyz.torquato.myapps.ui.activities.CypherActivity
import xyz.torquato.myapps.ui.activities.EngineActivity
import xyz.torquato.myapps.ui.menu.model.MenuUiState
import xyz.torquato.myapps.ui.menu.view.Menu
import xyz.torquato.myapps.ui.mixer.MusicActivity
import xyz.torquato.myapps.ui.navigation.ScreenRoute

@Composable
fun SetupNavigator(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Menu
    ) {
        activity<ScreenRoute.Home> {
            label = "Music"
            activityClass = MusicActivity::class
        }

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

        activity<ScreenRoute.Cypher> {
            label = "Cypher"
            activityClass = CypherActivity::class
        }

        composable<ScreenRoute.Menu> {
            Menu(
                uiState = MenuUiState(
                    listOf(
                        MenuUiState.MenuEntry(
                            title = "Mixer",
                            destination = ScreenRoute.FrequencySelector,
                            isSelected = false
                        ),
                        MenuUiState.MenuEntry(
                            title = "Game",
                            destination = ScreenRoute.Engine,
                            isSelected = false
                        ),
                        MenuUiState.MenuEntry(
                            title = "Cypher",
                            destination = ScreenRoute.Cypher,
                            isSelected = false
                        )
                    )
                ),
                onSelect = navController::navigate,
            )
        }
    }
}