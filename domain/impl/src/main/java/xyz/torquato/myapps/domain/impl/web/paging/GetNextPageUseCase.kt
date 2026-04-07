package xyz.torquato.myapps.domain.impl.web.paging

import xyz.torquato.myapps.domain.api.pagging.IPagingRepository
import xyz.torquato.myapps.domain.api.web.IQueryRepository

class GetNextPageUseCase(
    private val repository: IPagingRepository,
    private val queryRepository: IQueryRepository
) {

    operator fun invoke() {
        val id = queryRepository.getLast()
        println("MyTag: [US] NEXT PAGE $id")
        repository.getNextPage(id, 10)
    }
}