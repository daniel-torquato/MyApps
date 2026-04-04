package xyz.torquato.myaps.domain.api.encrypt

import kotlinx.coroutines.flow.StateFlow

interface ICypherRepository {

    val message: StateFlow<String>

    fun setToken(newToken: String)

    fun setMessage(newMessage: String)
}