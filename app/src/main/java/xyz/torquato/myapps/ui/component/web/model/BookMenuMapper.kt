package xyz.torquato.myapps.ui.component.web.model

import xyz.torquato.myaps.domain.api.web.model.BookItem

object BookMenuMapper {

    fun List<BookItem>.toUiState(): List<BookMenuUiState.BookItem> = map {
        it.toUiState()
    }

    fun BookItem.toUiState(): BookMenuUiState.BookItem =
        BookMenuUiState.BookItem(
            id = id,
            title = title,
            author = author,
            description = description,
            smallThumbnailUrl = smallThumbnailUrl,
            largeThumbnailUrl = largeThumbnailUrl,
            buyLink = buyLink
        )
}