package xyz.torquato.myapps.domain.api.web.model

data class QueryRequest(
    val query: String,
    val start: Int,
    val length: Int
)