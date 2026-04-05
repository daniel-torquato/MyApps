package xyz.torquato.myaps.domain.impl.cypher

import xyz.torquato.myaps.domain.api.encrypt.ICypherRepository

class SetMessageUseCase(
    private val repository: ICypherRepository
) {

    operator fun invoke(message: String) {
        repository.setMessage(message)
    }
}