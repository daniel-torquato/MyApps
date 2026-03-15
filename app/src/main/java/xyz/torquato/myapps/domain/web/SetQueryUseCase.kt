package xyz.torquato.myapps.domain.web

import xyz.torquato.myapps.api.web.IQueryRepository
import xyz.torquato.myapps.api.web.model.QueryRequest

class SetQueryUseCase(
    private val queryRepository: IQueryRepository
) {
    operator fun invoke(query: String) {
        println("MyTag: Query $query")
        queryRepository.setQuery(QueryRequest(query, 0, 10))
    }
}