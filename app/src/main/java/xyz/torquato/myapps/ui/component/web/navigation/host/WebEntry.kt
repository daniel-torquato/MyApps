package xyz.torquato.myapps.ui.component.web.navigation.host

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

object WebEntry {

    @Composable
    fun Handler()  {
        val navController = rememberNavController()
        WebNavigator.WebNavGraph(navController)
    }
}