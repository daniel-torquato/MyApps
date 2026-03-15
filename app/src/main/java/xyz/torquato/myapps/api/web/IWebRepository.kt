package xyz.torquato.myapps.api.web

import xyz.torquato.myapps.api.web.model.QueryRequest
import xyz.torquato.myapps.api.web.model.QueryResult

interface IWebRepository {

    suspend fun request(queryRequest: QueryRequest): QueryResult
}