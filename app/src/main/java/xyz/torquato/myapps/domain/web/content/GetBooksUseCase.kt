package xyz.torquato.myapps.domain.web.content

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import org.json.JSONArray
import org.json.JSONObject
import xyz.torquato.myapps.api.pagging.IPagingRepository
import xyz.torquato.myapps.api.web.IQueryRepository
import xyz.torquato.myapps.api.web.IWebRepository
import xyz.torquato.myapps.api.web.cache.CacheItem
import xyz.torquato.myapps.api.web.cache.IWebCacheRepository
import xyz.torquato.myapps.api.web.model.QueryRequest
import xyz.torquato.myapps.api.web.model.QueryResult
import xyz.torquato.myapps.domain.web.content.model.QueryResultCollection

class GetBooksUseCase(
    private val queryRepository: IQueryRepository,
    private val webRepository: IWebRepository,
    private val pagingRepository: IPagingRepository,
    private val webCacheRepository: IWebCacheRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<QueryResultCollection.Valid.BookItem>> =
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



    private fun List<QueryResultCollection.Valid.BookItem>.toCache(): List<CacheItem> = map {
        it.toCache()
    }

    private fun QueryResultCollection.Valid.BookItem.toCache(): CacheItem = CacheItem(
        id,
        title,
        author,
        description,
        smallThumbnailUrl,
        largeThumbnailUrl,
        buyLink
    )

    private fun QueryResultCollection.toData(): List<QueryResultCollection.Valid.BookItem> = when (this) {
        is QueryResultCollection.Valid -> {
            content.map { result -> result.toData() }.reduce { acc, new -> acc + new }
        }

        else -> emptyList()
    }

    private fun QueryResult.toData(): List<QueryResultCollection.Valid.BookItem> {
        return when(this) {
            is QueryResult.Valid -> {

                val pullString: JSONObject.(String) -> String = { id ->
                    runCatching { getString(id) }.getOrNull().orEmpty()
                }

                val fillString: JSONObject?.(String) -> String = { id ->
                    this?.pullString(id).orEmpty()
                }

                val pullObject: JSONObject.(String) -> JSONObject? = { id ->
                    runCatching { getJSONObject(id) }.getOrNull()
                }

                val pullArray: JSONObject.(String) -> JSONArray? = { id ->
                    runCatching { getJSONArray(id) }.getOrNull()
                }
                val result = mutableListOf<QueryResultCollection.Valid.BookItem>()

                val items = data.pullArray("items")
                if (items != null) {
                    repeat(items.length()) { index ->
                        val element = items.getJSONObject(index)


                        val volumeInfo = element.pullObject("volumeInfo")

                        val imageInfo = volumeInfo?.pullObject("imageLinks")

                        val saleInfo = element.pullObject("saleInfo")

                        val item = QueryResultCollection.Valid.BookItem(
                            id = element.pullString("id"),
                            title = volumeInfo.fillString("title"),
                            author = volumeInfo.fillString("authors"),
                            description = volumeInfo.fillString("description"),
                            smallThumbnailUrl = imageInfo.fillString("smallThumbnail"),
                            largeThumbnailUrl = imageInfo.fillString("thumbnail"),
                            buyLink = saleInfo.fillString("saleability")
                                .takeIf { saleability -> saleability == "FOR_SALE" }
                                .let { saleInfo.fillString("buyLink") }
                        )
                        result.add(item)
                    }
                }
                result.toList()
            }
            else -> {
                emptyList()
            }
        }
    }
}