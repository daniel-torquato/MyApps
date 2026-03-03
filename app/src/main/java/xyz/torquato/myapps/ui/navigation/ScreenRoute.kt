package xyz.torquato.myapps.ui.navigation

import kotlinx.serialization.Serializable

sealed interface ScreenRoute {

    @Serializable
    data object Menu : ScreenRoute

    @Serializable
    data object Home : ScreenRoute

    @Serializable
    data object FrequencySelector : ScreenRoute

    @Serializable
    data object Engine: ScreenRoute

    @Serializable
    data object Cypher: ScreenRoute
}