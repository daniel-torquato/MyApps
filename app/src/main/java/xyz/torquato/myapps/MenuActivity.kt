package xyz.torquato.myapps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import xyz.torquato.myapps.ui.navigation.host.SetupNavigator
import xyz.torquato.myapps.ui.theme.MyAppsTheme

@AndroidEntryPoint
class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppsTheme {
                val navController = rememberNavController()
                SetupNavigator(navController)
            }
        }
    }
}