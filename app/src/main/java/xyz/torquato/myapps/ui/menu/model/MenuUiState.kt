package xyz.torquato.myapps.ui.menu.model

import xyz.torquato.myapps.ui.navigation.ScreenRoute

data class MenuUiState(
    val entries: List<MenuEntry>
) {
    data class MenuEntry(
        val title: String,
        val destination: ScreenRoute,
        val isSelected: Boolean
    )
}