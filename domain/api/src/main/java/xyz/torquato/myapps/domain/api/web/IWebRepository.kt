package xyz.torquato.myapps.domain.api.web

import xyz.torquato.myapps.domain.api.web.model.QueryRequest
import xyz.torquato.myapps.domain.api.web.model.QueryResult

interface IWebRepository {

    suspend fun request(queryRequest: QueryRequest): QueryResult
}