package xyz.torquato.myapps.ui.component.web.navigation.host

import kotlinx.serialization.Serializable

sealed interface WebRoute {

    @Serializable
    data object Search: WebRoute

    @Serializable
    data object FullDescription: WebRoute
}