package xyz.torquato.myapps.domain.web.pagging

import xyz.torquato.myaps.domain.api.pagging.IPagingRepository

class GetPagerUseCase(
    private val repository: IPagingRepository
) {

    operator fun invoke(id: String) = repository.getPager(id)

}