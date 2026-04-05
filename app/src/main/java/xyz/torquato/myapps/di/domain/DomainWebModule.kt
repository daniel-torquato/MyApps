package xyz.torquato.myapps.di.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.torquato.myapps.domain.web.content.GetBooksUseCase
import xyz.torquato.myapps.domain.web.content.GetCacheBooksUseCase
import xyz.torquato.myaps.domain.api.pagging.IPagingRepository
import xyz.torquato.myaps.domain.api.web.IQueryRepository
import xyz.torquato.myaps.domain.api.web.IWebRepository
import xyz.torquato.myaps.domain.api.web.cache.IWebCacheRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainWebModule {

    @Singleton
    @Provides
    fun provideGetBooksUseCase(
        queryRepository: IQueryRepository,
        webRepository: IWebRepository,
        pagingRepository: IPagingRepository,
        webCacheRepository: IWebCacheRepository
    ): GetBooksUseCase = GetBooksUseCase(
        queryRepository,
        webRepository,
        pagingRepository,
        webCacheRepository
    )

    @Singleton
    @Provides
    fun provideGetCacheBooksUseCase(
        webCacheRepository: IWebCacheRepository
    ): GetCacheBooksUseCase = GetCacheBooksUseCase(
        webCacheRepository
    )




}