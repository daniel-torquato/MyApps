package xyz.torquato.myapps.ui.component.web.navigation.host

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import xyz.torquato.myapps.ui.component.web.view.WebFullDescription
import xyz.torquato.myapps.ui.component.web.view.WebPrev

object WebNavigator {

    @Composable
    fun WebNavGraph(
        navController: NavHostController
    ) {
        NavHost(
            navController = navController,
            startDestination = WebRoute.Search
        ) {
            composable<WebRoute.Search> { backStackEntry ->
                val router = WebPrev.Router(navController)
                WebPrev.Builder(viewModelStoreOwner = backStackEntry, router = router)
            }

            composable<WebRoute.FullDescription> { backStackEntry ->
                WebFullDescription.Builder(backStackEntry)
            }
        }
    }

    abstract class Router(
        private val controller: NavHostController
    ) {

        fun navigateBack() {
            controller.popBackStack()
        }
    }
}