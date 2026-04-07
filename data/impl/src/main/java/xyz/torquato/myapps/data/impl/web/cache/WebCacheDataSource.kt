package xyz.torquato.myapps.data.impl.web.cache

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import xyz.torquato.myapps.domain.api.web.cache.CacheItem
import javax.inject.Inject

class WebCacheDataSource @Inject constructor() {

    private val _cache = MutableStateFlow<List<CacheItem>>(emptyList())

    val cache = _cache.asStateFlow()

    fun save(items: List<CacheItem>) {
        _cache.value = items
    }
}