package xyz.torquato.myaps.domain.api.web.cache

data class CacheItem(
    var id: String,
    var title: String,
    var author: String,
    var description: String,
    var smallThumbnailUrl: String,
    var largeThumbnailUrl: String,
    var buyLink: String?
)
