package xyz.torquato.myapps.data.web

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import xyz.torquato.myapps.api.web.IQueryRepository
import xyz.torquato.myapps.api.web.model.QueryRequest
import javax.inject.Inject

class QueryRepository @Inject constructor(
    private val dataSource: QueryDataSource,
): IQueryRepository {

    override val queryRequest: Flow<QueryRequest> = combine(
        dataSource.query,
        dataSource.start,
        dataSource.length,
        ::QueryRequest
    )

    override fun setQuery(request: QueryRequest) {
        println("MyTag: Repo $request")
        dataSource.setAll(request.query, request.start, request.length)
    }
}