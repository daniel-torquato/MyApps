package xyz.torquato.myapps.domain.web.content.model

import xyz.torquato.myapps.api.web.model.QueryResult

sealed interface QueryResultCollection {

    data object Empty: QueryResultCollection

    data class Valid(
        val content: List<QueryResult.Valid>
    ): QueryResultCollection {
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

    data object Error: QueryResultCollection



}