package xyz.torquato.myapps.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.torquato.myapps.domain.api.encrypt.ICypherRepository
import xyz.torquato.myapps.domain.api.pagging.IPagingRepository
import xyz.torquato.myapps.domain.api.sound.ISoundRepository
import xyz.torquato.myapps.domain.api.web.ILocalWebRepository
import xyz.torquato.myapps.domain.api.web.IQueryRepository
import xyz.torquato.myapps.domain.api.web.IWebRepository
import xyz.torquato.myapps.domain.api.web.cache.IWebCacheRepository
import xyz.torquato.myapps.domain.impl.cypher.GetAuthenticationUseCase
import xyz.torquato.myapps.domain.impl.cypher.SetMessageUseCase
import xyz.torquato.myapps.domain.impl.cypher.SetTokenUseCase
import xyz.torquato.myapps.domain.impl.mixer.CleanUpUseCase
import xyz.torquato.myapps.domain.impl.mixer.PerformControlUseCase
import xyz.torquato.myapps.domain.impl.mixer.SetChannelStateUseCase
import xyz.torquato.myapps.domain.impl.mixer.SetToneUseCase
import xyz.torquato.myapps.domain.impl.mixer.SetTonesUseCase
import xyz.torquato.myapps.domain.impl.web.GetCacheBooksUseCase
import xyz.torquato.myapps.domain.impl.web.GetSelectedBookUseCase
import xyz.torquato.myapps.domain.impl.web.SetQueryUseCase
import xyz.torquato.myapps.domain.impl.web.content.GetBooksUseCase
import xyz.torquato.myapps.domain.impl.web.content.SetSelectedBookUseCase
import xyz.torquato.myapps.domain.impl.web.paging.GetNextPageUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseImpl {

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

    @Singleton
    @Provides
    fun provideGetQueryUseCase(
        queryRepository: IQueryRepository,
    ): SetQueryUseCase = SetQueryUseCase(
        queryRepository
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
    fun provideGetCacheBooksUseCase(
        webCacheRepository: IWebCacheRepository
    ): GetCacheBooksUseCase = GetCacheBooksUseCase(
        webCacheRepository
    )

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
    fun provideGetAuthenticationUseCae(
        cypherRepository: ICypherRepository
    ): GetAuthenticationUseCase = GetAuthenticationUseCase(
        cypherRepository
    )


    @Singleton
    @Provides
    fun provideSetTokenUseCase(
        cypherRepository: ICypherRepository
    ): SetTokenUseCase = SetTokenUseCase(
        cypherRepository
    )

    @Singleton
    @Provides
    fun provideSetMessageUseCase(
        cypherRepository: ICypherRepository
    ): SetMessageUseCase = SetMessageUseCase(
        cypherRepository
    )

    @Singleton
    @Provides
    fun provideSetToneUseCase(
        soundRepository: ISoundRepository
    ): SetToneUseCase = SetToneUseCase(
        soundRepository
    )

    @Singleton
    @Provides
    fun provideSetTonesUseCase(
        soundRepository: ISoundRepository
    ): SetTonesUseCase = SetTonesUseCase(
        soundRepository
    )

    @Singleton
    @Provides
    fun providePerformControlUseCase(
        soundRepository: ISoundRepository
    ): PerformControlUseCase = PerformControlUseCase(
        soundRepository
    )

    @Singleton
    @Provides
    fun provideCleanUpCase(
        soundRepository: ISoundRepository
    ): CleanUpUseCase = CleanUpUseCase(
        soundRepository
    )

    @Singleton
    @Provides
    fun provideSetChannelStateUseCae(
        soundRepository: ISoundRepository
    ): SetChannelStateUseCase = SetChannelStateUseCase(
        soundRepository
    )


}