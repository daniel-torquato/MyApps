package xyz.torquato.myapps.domain.web.content

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import xyz.torquato.myapps.api.web.IQueryRepository
import xyz.torquato.myapps.api.web.cache.CacheItem
import xyz.torquato.myapps.api.web.cache.IWebCacheRepository
import xyz.torquato.myapps.domain.web.content.model.QueryResultCollection
import xyz.torquato.myaps.domain.api.pagging.IPagingRepository
import xyz.torquato.myaps.domain.api.web.IWebRepository
import xyz.torquato.myaps.domain.api.web.model.BookItem
import xyz.torquato.myaps.domain.api.web.model.QueryRequest
import xyz.torquato.myaps.domain.api.web.model.QueryResult

class GetBooksUseCase(
    private val queryRepository: IQueryRepository,
    private val webRepository: IWebRepository,
    private val pagingRepository: IPagingRepository,
    private val webCacheRepository: IWebCacheRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<BookItem>> =
        queryRepository.queryRequest.onEach {
            println("MyTag: [UC] NEW QUERY $it")
        }.flatMapLatest { query ->
            pagingRepository.addPager(query)
            pagingRepository.getPager(query).map { range ->
                println("MyTag: [UC] Update Page $query $range")
                QueryRequest(query, range.first, (range.last - range.first + 1))
            }.flatMapMerge { queryRequest ->
                suspend { webRepository.request(queryRequest) }.asFlow()
            }.onEach {
                println("MyTag: ${this::class.simpleName} Content Request $it")
            }.scan<QueryResult, QueryResultCollection>(QueryResultCollection.Empty) { acc, new ->
                when {
                    new is QueryResult.Error -> {
                        QueryResultCollection.Error
                    }

                    new is QueryResult.Valid && acc is QueryResultCollection.Valid -> {
                        acc.copy(
                            content = acc.content + new
                        )
                    }

                    new is QueryResult.Valid && acc is QueryResultCollection.Empty -> {
                        QueryResultCollection.Valid(
                            content = listOf(new)
                        )
                    }

                    new is QueryResult.Valid && acc is QueryResultCollection.Error -> {
                        QueryResultCollection.Valid(
                            content = listOf(new)
                        )
                    }

                    else -> acc
                }
            }.map {
                it.toData()
            }
        }.onEach {
            webCacheRepository.save(it.toCache())
        }



    private fun List<BookItem>.toCache(): List<CacheItem> = map {
        it.toCache()
    }

    private fun BookItem.toCache(): CacheItem = CacheItem(
        id,
        title,
        author,
        description,
        smallThumbnailUrl,
        largeThumbnailUrl,
        buyLink
    )

    private fun QueryResultCollection.toData(): List<BookItem> = when (this) {
        is QueryResultCollection.Valid -> {
            content.map { result -> result.toData() }.reduce { acc, new -> acc + new }
        }

        else -> emptyList()
    }

    private fun QueryResult.toData(): List<BookItem> {
        return when(this) {
            is QueryResult.Valid -> { data }
            else -> {
                emptyList()
            }
        }
    }

}