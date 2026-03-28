package xyz.torquato.myapps.data.web

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QueryDataSource @Inject constructor() {

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    fun setAll(
        query: String,
    ) {
        println("MyTag: [DS] SET QUERY $query")
        _query.value = query
    }

}