package xyz.torquato.myapps.data.impl.paging

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import xyz.torquato.myapps.data.impl.external.PagingDataSource
import xyz.torquato.myaps.domain.api.pagging.IPagingRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PagingRepository @Inject constructor(
    private val pagingDataSource: PagingDataSource
) : IPagingRepository {

    override fun getPager(id: String): Flow<IntRange> {
        return pagingDataSource.getPager(id).onEach {
            println("MyTag: [R] NEW PAGE $it")
        }
    }

    override fun getNextPage(id: String, size: Int) {
        pagingDataSource.nextPage(id, size)
    }

    override fun addPager(id: String) {
        pagingDataSource.addPager(id)
    }

    override fun removePager(id: String) {

    }

}