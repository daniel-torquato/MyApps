package xyz.torquato.myapps.domain.impl.web

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.torquato.myapps.domain.api.web.cache.CacheItem
import xyz.torquato.myapps.domain.api.web.cache.IWebCacheRepository
import xyz.torquato.myapps.domain.impl.web.content.model.BookItem

class GetCacheBooksUseCase(
    val webCacheRepository: IWebCacheRepository
) {

    operator fun invoke(): Flow<List<BookItem>> = webCacheRepository.cache.map {
        it.toDomain()
    }

    private fun List<CacheItem>.toDomain(): List<BookItem> = map {
        it.toDomain()
    }

    private fun CacheItem.toDomain(): BookItem = BookItem(
        id,
        title,
        author,
        description,
        smallThumbnailUrl,
        largeThumbnailUrl,
        buyLink
    )
}