package xyz.torquato.myapps.data.impl.paging.model

sealed interface PageRequest {

    data class Fixed(
        val size: Int
    ): PageRequest
}