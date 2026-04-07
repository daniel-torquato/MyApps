package xyz.torquato.myapps.domain.impl.web.content.model

import xyz.torquato.myapps.domain.api.web.model.QueryResult

sealed interface QueryResultCollection {

    data object Empty: QueryResultCollection

    data class Valid(
        val content: List<QueryResult.Valid>
    ): QueryResultCollection

    data object Error: QueryResultCollection



}