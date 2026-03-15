package xyz.torquato.myapps.data.web

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class QueryDataSource @Inject constructor() {

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _start = MutableStateFlow(0)
    val start = _start.asStateFlow()

    private val _length = MutableStateFlow(0)
    val length = _length.asStateFlow()

    fun setAll(
        query: String,
        start: Int,
        length: Int
    ) {
        _query.value = query
        _start.value = start
        _length.value = length
    }

}