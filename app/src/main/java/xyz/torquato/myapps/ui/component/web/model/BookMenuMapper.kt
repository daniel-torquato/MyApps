package xyz.torquato.myapps.ui.component.web.model

import xyz.torquato.myapps.domain.web.content.model.QueryResultCollection

object BookMenuMapper {

    fun List<QueryResultCollection.Valid.BookItem>.toUiState(): List<BookMenuUiState.BookItem> = map {
        it.toUiState()
    }

    fun QueryResultCollection.Valid.BookItem.toUiState(): BookMenuUiState.BookItem =
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