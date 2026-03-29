package xyz.torquato.myapps.ui.menu.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.torquato.myapps.ui.menu.model.MenuUiState
import xyz.torquato.myapps.ui.navigation.ScreenRoute
import xyz.torquato.myapps.ui.theme.MyAppsTheme

@Composable
fun Menu(
    uiState: MenuUiState,
    onSelect: (ScreenRoute) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        contentPadding = PaddingValues(50.dp),
    ) {
        items(items = uiState.entries) { (title, entry, isSelected) ->
            Button(
                onClick = { onSelect(entry) },
                modifier = Modifier
                    .testTag("BUTTON_${title}")
                    .size(100.dp),
                contentPadding = PaddingValues(10.dp),
                elevation = ButtonDefaults.buttonElevation(10.dp),
                shape = RoundedCornerShape(20)
            ) {
                Text(
                    title,
                    color = if (isSelected) Color.Red else Color.Blue
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyAppsTheme {
        Menu(
            uiState = MenuUiState(
                listOf(
                    MenuUiState.MenuEntry(
                        title = "Mixer",
                        destination = ScreenRoute.FrequencySelector,
                        isSelected = true
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
                    ),
                    MenuUiState.MenuEntry(
                        title = "Web",
                        destination = ScreenRoute.Web,
                        isSelected = false
                    )
                )
            ),
            onSelect = {},
        )
    }
}