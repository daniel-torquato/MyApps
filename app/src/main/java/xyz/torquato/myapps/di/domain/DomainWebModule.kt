package xyz.torquato.myapps.di.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.torquato.myapps.api.web.ILocalWebRepository
import xyz.torquato.myapps.api.web.IQueryRepository
import xyz.torquato.myapps.api.web.IWebRepository
import xyz.torquato.myapps.data.web.QueryRepository
import xyz.torquato.myapps.data.web.WebRepository
import xyz.torquato.myapps.domain.web.GetBooksUseCase
import xyz.torquato.myapps.domain.web.GetSelectedBookUseCase
import xyz.torquato.myapps.domain.web.SetSelectedBookUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainWebModule {

    @Singleton
    @Provides
    fun provideGetBooksUseCase(
        queryRepository: IQueryRepository,
        webRepository: IWebRepository
    ): GetBooksUseCase = GetBooksUseCase(
        queryRepository,
        webRepository
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
}