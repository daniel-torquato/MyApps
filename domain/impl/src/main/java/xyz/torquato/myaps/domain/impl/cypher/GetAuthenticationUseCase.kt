package xyz.torquato.myaps.domain.impl.cypher

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.torquato.myaps.domain.api.encrypt.ICypherRepository

class GetAuthenticationUseCase(
    private val repository: ICypherRepository
) {

    operator fun invoke(): Flow<String> = repository.message.map { it }
}