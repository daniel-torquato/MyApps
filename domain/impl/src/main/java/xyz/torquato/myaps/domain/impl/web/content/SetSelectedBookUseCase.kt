package xyz.torquato.myaps.domain.impl.web.content

import xyz.torquato.myaps.domain.api.web.ILocalWebRepository

class SetSelectedBookUseCase(
    private val repository: ILocalWebRepository
) {
    operator fun invoke(id: String) = repository.setSelectedBookId(id)
}