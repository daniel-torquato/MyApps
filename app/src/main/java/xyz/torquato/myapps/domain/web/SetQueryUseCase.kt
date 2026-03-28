package xyz.torquato.myapps.domain.web

import xyz.torquato.myapps.api.web.IQueryRepository

class SetQueryUseCase(
    private val queryRepository: IQueryRepository
) {
    operator fun invoke(query: String) {
        println("MyTag: Query $query")
        queryRepository.setQuery(query)
    }
}