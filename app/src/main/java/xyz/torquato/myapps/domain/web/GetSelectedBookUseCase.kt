package xyz.torquato.myapps.domain.web

import xyz.torquato.myaps.domain.api.web.ILocalWebRepository

class GetSelectedBookUseCase(
    private val repository: ILocalWebRepository
) {
    operator fun invoke() = repository.selectedBookId
}