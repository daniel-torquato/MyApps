package xyz.torquato.myaps.domain.api.pagging

import kotlinx.coroutines.flow.Flow

interface IPagingRepository {

    fun getPager(id: String): Flow<IntRange>

    fun getNextPage(id: String, size: Int)

    fun addPager(id: String)

    fun removePager(id: String)
}