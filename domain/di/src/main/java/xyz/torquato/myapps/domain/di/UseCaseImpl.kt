package xyz.torquato.myapps.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.torquato.myaps.domain.api.pagging.IPagingRepository
import xyz.torquato.myaps.domain.api.web.ILocalWebRepository
import xyz.torquato.myaps.domain.api.web.IQueryRepository
import xyz.torquato.myaps.domain.impl.web.GetSelectedBookUseCase
import xyz.torquato.myaps.domain.impl.web.SetQueryUseCase
import xyz.torquato.myaps.domain.impl.web.content.SetSelectedBookUseCase
import xyz.torquato.myaps.domain.impl.web.paging.GetNextPageUseCase
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
}