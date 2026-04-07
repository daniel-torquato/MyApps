package xyz.torquato.myapps.data.impl.web

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import xyz.torquato.myapps.domain.api.web.IWebRepository
import xyz.torquato.myapps.domain.api.web.model.BookError
import xyz.torquato.myapps.domain.api.web.model.BookItem
import xyz.torquato.myapps.domain.api.web.model.QueryRequest
import xyz.torquato.myapps.domain.api.web.model.QueryResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebRepository @Inject constructor(
    private val dataSource: WebDataSource
): IWebRepository {

    override suspend fun request(queryRequest: QueryRequest): QueryResult =
        withContext(Dispatchers.IO) {
            println("MyTag: New Request $queryRequest")
            when (val result =
                dataSource.search(queryRequest.query, queryRequest.start, queryRequest.length)) {
                is JSONObject -> QueryResult.Valid(result.toDomain())
                is JSONException -> QueryResult.Error(result.toDomain())
                else -> QueryResult.Empty
            }.also {
                println("MyTag: New Result $it")
            }
        }

    private fun JSONObject.toDomain(): List<BookItem> {
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
        val result = mutableListOf<BookItem>()

        val items = pullArray("items")
        if (items != null) {
            repeat(items.length()) { index ->
                val element = items.getJSONObject(index)

                val volumeInfo = element.pullObject("volumeInfo")

                val imageInfo = volumeInfo?.pullObject("imageLinks")

                val saleInfo = element.pullObject("saleInfo")

                val item = BookItem(
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
        return result.toList()
    }

    private fun JSONException.toDomain(): BookError {
        return BookError(message ?: "Exception without message")
    }
}