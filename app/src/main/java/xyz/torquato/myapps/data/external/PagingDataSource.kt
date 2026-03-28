package xyz.torquato.myapps.data.external

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class PagingDataSource @Inject constructor() {

    private val pages = mutableMapOf<String, MutableStateFlow<IntRange>>()

    fun addPager(id: String) {
        println("MyTag: [DS] ADD PAGER $id")
        pages[id] = MutableStateFlow(0 until  DEFAULT_COUNT)
    }

    fun nextPage(id: String, size: Int) {
        pages[id]?.update {
            (it.last + 1) .. (it.last + size)
        }
    }

    fun getPager(id: String): Flow<IntRange> = pages[id]?.map { it } ?: emptyFlow()


    companion object {
        const val DEFAULT_COUNT = 10
    }

}