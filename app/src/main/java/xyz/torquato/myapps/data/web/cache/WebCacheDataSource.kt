package xyz.torquato.myapps.data.web.cache

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import xyz.torquato.myapps.api.web.cache.CacheItem
import javax.inject.Inject

class WebCacheDataSource @Inject constructor() {

    private val _cache = MutableStateFlow<List<CacheItem>>(emptyList())

    val cache = _cache.asStateFlow()

    fun save(items: List<CacheItem>) {
        _cache.value = items
    }
}