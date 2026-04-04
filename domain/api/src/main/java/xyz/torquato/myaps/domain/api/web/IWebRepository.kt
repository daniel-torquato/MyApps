package xyz.torquato.myaps.domain.api.web

import xyz.torquato.myaps.domain.api.web.model.QueryRequest
import xyz.torquato.myaps.domain.api.web.model.QueryResult

interface IWebRepository {

    suspend fun request(queryRequest: QueryRequest): QueryResult
}