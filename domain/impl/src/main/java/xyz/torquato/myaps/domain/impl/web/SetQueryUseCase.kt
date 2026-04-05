package xyz.torquato.myaps.domain.impl.web

import xyz.torquato.myaps.domain.api.web.IQueryRepository

class SetQueryUseCase(
    private val queryRepository: IQueryRepository
) {
    operator fun invoke(query: String) {
        println("MyTag: Query $query")
        queryRepository.setQuery(query)
    }
}