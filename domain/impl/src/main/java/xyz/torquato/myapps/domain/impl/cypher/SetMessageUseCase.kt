package xyz.torquato.myapps.domain.impl.cypher

import xyz.torquato.myapps.domain.api.encrypt.ICypherRepository

class SetMessageUseCase(
    private val repository: ICypherRepository
) {

    operator fun invoke(message: String) {
        repository.setMessage(message)
    }
}