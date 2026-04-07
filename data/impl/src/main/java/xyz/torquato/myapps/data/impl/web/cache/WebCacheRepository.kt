package xyz.torquato.myapps.data.impl.web.cache

import kotlinx.coroutines.flow.StateFlow
import xyz.torquato.myapps.domain.api.web.cache.CacheItem
import xyz.torquato.myapps.domain.api.web.cache.IWebCacheRepository
import javax.inject.Inject

class WebCacheRepository @Inject constructor(
    val dataSource: WebCacheDataSource,
) : IWebCacheRepository {

    override val cache: StateFlow<List<CacheItem>> = dataSource.cache

    override fun save(items: List<CacheItem>) {
        dataSource.save(items)
    }
}