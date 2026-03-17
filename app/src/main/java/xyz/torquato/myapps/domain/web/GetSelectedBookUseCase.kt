package xyz.torquato.myapps.domain.web

import xyz.torquato.myapps.api.web.ILocalWebRepository
import xyz.torquato.myapps.api.web.IQueryRepository
import xyz.torquato.myapps.api.web.model.QueryRequest

class GetSelectedBookUseCase(
    private val repository: ILocalWebRepository
) {
    operator fun invoke() = repository.selectedBookId
}