package xyz.torquato.myapps.api.web

interface IWebRepository {

    fun request(query: String): Boolean
}