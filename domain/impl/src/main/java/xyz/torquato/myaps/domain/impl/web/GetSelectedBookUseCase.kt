package xyz.torquato.myaps.domain.impl.web

import kotlinx.coroutines.flow.Flow
import xyz.torquato.myaps.domain.api.web.ILocalWebRepository

class GetSelectedBookUseCase(
    private val repository: ILocalWebRepository
) {
    operator fun invoke(): Flow<String> = repository.selectedBookId
}