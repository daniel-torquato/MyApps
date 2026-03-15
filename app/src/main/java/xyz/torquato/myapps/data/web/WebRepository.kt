package xyz.torquato.myapps.data.web

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import xyz.torquato.myapps.api.web.IWebRepository
import xyz.torquato.myapps.api.web.model.QueryRequest
import xyz.torquato.myapps.api.web.model.QueryResult
import javax.inject.Inject

class WebRepository @Inject constructor(
    private val dataSource: WebDataSource
): IWebRepository {

    override suspend fun request(queryRequest: QueryRequest): QueryResult =
        withContext(Dispatchers.IO) {
            println("MyTag: New Request ${queryRequest}")
            when (val result = dataSource.search(queryRequest.query, queryRequest.start, queryRequest.length)) {
                is JSONObject -> QueryResult.Valid(result)
                is JSONException -> QueryResult.Error(result)
                else -> QueryResult.Empty
            }.also {
                println("MyTag: New Result ${it}")
            }
        }
}