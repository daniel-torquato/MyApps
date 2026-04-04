package xyz.torquato.myapps.domain.web

import xyz.torquato.myapps.api.web.ILocalWebRepository

class SetSelectedBookUseCase(
    private val repository: ILocalWebRepository
) {
    operator fun invoke(id: String) = repository.setSelectedBookId(id)
}