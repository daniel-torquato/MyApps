package xyz.torquato.myapps.domain.web.pagging

import xyz.torquato.myaps.domain.api.pagging.IPagingRepository
import xyz.torquato.myaps.domain.api.web.IQueryRepository

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