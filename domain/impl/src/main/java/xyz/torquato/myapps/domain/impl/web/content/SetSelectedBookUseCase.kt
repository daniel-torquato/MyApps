package xyz.torquato.myapps.domain.impl.web.content

import xyz.torquato.myapps.domain.api.web.ILocalWebRepository

class SetSelectedBookUseCase(
    private val repository: ILocalWebRepository
) {
    operator fun invoke(id: String) = repository.setSelectedBookId(id)
}