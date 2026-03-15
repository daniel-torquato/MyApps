package xyz.torquato.myapps.api.web

import kotlinx.coroutines.flow.Flow
import xyz.torquato.myapps.api.web.model.QueryRequest

interface IQueryRepository {

    val queryRequest: Flow<QueryRequest>

    fun setQuery(request: QueryRequest)
}