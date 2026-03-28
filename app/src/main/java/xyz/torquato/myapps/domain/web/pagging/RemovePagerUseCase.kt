package xyz.torquato.myapps.domain.web.pagging

import xyz.torquato.myapps.api.pagging.IPagingRepository

class RemovePagerUseCase(
    private val repository: IPagingRepository
) {

    operator fun invoke(id: String) = repository.removePager(id)
}