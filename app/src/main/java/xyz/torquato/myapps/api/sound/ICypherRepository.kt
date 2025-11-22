package xyz.torquato.myapps.api.sound

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ICypherRepository {

    val message: StateFlow<String>

    fun setToken(newToken: String)

    fun setMessage(newMessage: String)
}