package xyz.torquato.myapps.api.web

import kotlinx.coroutines.flow.Flow

interface IQueryRepository {

    val queryRequest: Flow<String>

    fun getLast(): String

    fun setQuery(request: String)
}