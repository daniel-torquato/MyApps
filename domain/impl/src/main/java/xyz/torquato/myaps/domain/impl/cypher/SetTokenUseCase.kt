package xyz.torquato.myaps.domain.impl.cypher

import xyz.torquato.myaps.domain.api.encrypt.ICypherRepository

class SetTokenUseCase(
    private val repository: ICypherRepository
) {

    operator fun invoke(token: String) {
        repository.setToken(token)
    }
}