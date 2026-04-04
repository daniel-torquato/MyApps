package xyz.torquato.myapps.domain.web.content.model

import xyz.torquato.myapps.api.web.model.QueryResult

sealed interface QueryResultCollection {

    data object Empty: QueryResultCollection

    data class Valid(
        val content: List<QueryResult.Valid>
    ): QueryResultCollection

    data object Error: QueryResultCollection



}