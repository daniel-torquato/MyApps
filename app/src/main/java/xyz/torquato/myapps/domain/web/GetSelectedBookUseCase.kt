package xyz.torquato.myapps.domain.web

import xyz.torquato.myapps.api.web.ILocalWebRepository

class GetSelectedBookUseCase(
    private val repository: ILocalWebRepository
) {
    operator fun invoke() = repository.selectedBookId
}