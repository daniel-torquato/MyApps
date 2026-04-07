package xyz.torquato.myapps.domain.api.web.model

sealed interface QueryResult {

    data object Empty: QueryResult

    data class Valid(val data: List<BookItem>): QueryResult

    data class Error(val error: BookError): QueryResult
}