package xyz.torquato.myapps.domain.api.web.cache

import kotlinx.coroutines.flow.StateFlow

interface IWebCacheRepository {

    val cache: StateFlow<List<CacheItem>>

    fun save(items: List<CacheItem>)
}