package xyz.torquato.myapps.domain.web

import xyz.torquato.myapps.api.web.ILocalWebRepository
import xyz.torquato.myapps.api.web.IQueryRepository
import xyz.torquato.myapps.api.web.model.QueryRequest

class SetSelectedBookUseCase(
    private val repository: ILocalWebRepository
) {
    operator fun invoke(id: String) = repository.setSelectedBookId(id)
}