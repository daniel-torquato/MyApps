package xyz.torquato.myapps.di.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.torquato.myapps.api.web.cache.IWebCacheRepository
import xyz.torquato.myapps.domain.web.GetSelectedBookUseCase
import xyz.torquato.myapps.domain.web.SetSelectedBookUseCase
import xyz.torquato.myapps.domain.web.content.GetBooksUseCase
import xyz.torquato.myapps.domain.web.content.GetCacheBooksUseCase
import xyz.torquato.myapps.domain.web.pagging.GetNextPageUseCase
import xyz.torquato.myaps.domain.api.pagging.IPagingRepository
import xyz.torquato.myaps.domain.api.web.ILocalWebRepository
import xyz.torquato.myaps.domain.api.web.IQueryRepository
import xyz.torquato.myaps.domain.api.web.IWebRepository
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

    @Singleton
    @Provides
    fun provideGetSelectedBookIdUseCase(
        repository: ILocalWebRepository
    ): GetSelectedBookUseCase = GetSelectedBookUseCase(
        repository
    )

    @Singleton
    @Provides
    fun provideSetSelectedBookIdUseCase(
        repository: ILocalWebRepository
    ): SetSelectedBookUseCase = SetSelectedBookUseCase(
        repository
    )

    @Singleton
    @Provides
    fun provideGetNextPageUseCase(
       repository: IPagingRepository,
       queryRepository: IQueryRepository
    ): GetNextPageUseCase = GetNextPageUseCase(
        repository,
        queryRepository
    )
}