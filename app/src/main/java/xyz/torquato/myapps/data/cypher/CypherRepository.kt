package xyz.torquato.myapps.data.cypher

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import xyz.torquato.myapps.api.sound.ICypherRepository
import javax.inject.Inject

class CypherRepository @Inject constructor() : ICypherRepository {

    private var lastToken: String = ""
    private var lastMessage: String = ""

    private val _message: MutableStateFlow<String> = MutableStateFlow("")

    override val message = _message.asStateFlow()

    private external fun entry(token: String, message: String): String

    override fun setMessage(newMessage: String) {
        lastMessage = newMessage
        _message.value = entry(lastToken, lastMessage)
    }

    override fun setToken(newToken: String) {
        lastToken = newToken
        _message.value = entry(lastToken, lastMessage)
    }

    init {
        System.loadLibrary("cypher")
    }
}