package xyz.torquato.myapps.presentation.viewmodel.web.model

data class BookMenuUiState(
    val content: List<BookItem>
) {
    data class BookItem(
        var id: String,
        var title: String,
        var author: String,
        var description: String,
        var smallThumbnailUrl: String,
        var largeThumbnailUrl: String,
        var buyLink: String?
    ) {
        companion object {
            fun empty() = BookItem(
                id = "",
                title = "",
                author = "",
                description = "",
                smallThumbnailUrl = "",
                largeThumbnailUrl = "",
                buyLink = null
            )
        }
    }
}
