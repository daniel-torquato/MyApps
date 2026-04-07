package xyz.torquato.myapps.domain.impl.cypher

import xyz.torquato.myapps.domain.api.encrypt.ICypherRepository

class SetTokenUseCase(
    private val repository: ICypherRepository
) {

    operator fun invoke(token: String) {
        repository.setToken(token)
    }
}