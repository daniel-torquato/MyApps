package xyz.torquato.myapps.domain.web.content

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.torquato.myapps.api.web.cache.CacheItem
import xyz.torquato.myapps.api.web.cache.IWebCacheRepository
import xyz.torquato.myapps.domain.web.content.model.QueryResultCollection
import kotlin.collections.map

class GetCacheBooksUseCase(
    val webCacheRepository: IWebCacheRepository
) {

    operator fun invoke(): Flow<List<QueryResultCollection.Valid.BookItem>> = webCacheRepository.cache.map {
        it.toDomain()
    }

    private fun List<CacheItem>.toDomain(): List<QueryResultCollection.Valid.BookItem> = map {
        it.toDomain()
    }

    private fun CacheItem.toDomain(): QueryResultCollection.Valid.BookItem = QueryResultCollection.Valid.BookItem(
        id,
        title,
        author,
        description,
        smallThumbnailUrl,
        largeThumbnailUrl,
        buyLink
    )
}