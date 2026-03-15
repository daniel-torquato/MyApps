package xyz.torquato.myapps.api.web.model

data class QueryRequest(
    val query: String,
    val start: Int,
    val length: Int
)