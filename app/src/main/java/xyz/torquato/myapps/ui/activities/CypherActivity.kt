package xyz.torquato.myapps.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import xyz.torquato.myapps.ui.component.cypher.CypherComponent
import xyz.torquato.myapps.ui.theme.MyAppsTheme

@AndroidEntryPoint
class CypherActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("MyTag: Music Mixer Created")
        setContent {
            MyAppsTheme {
                CypherComponent()
            }
        }
    }
}