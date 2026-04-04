package xyz.torquato.myapps.domain.web.pagging

import xyz.torquato.myapps.api.web.IQueryRepository
import xyz.torquato.myaps.domain.api.pagging.IPagingRepository

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