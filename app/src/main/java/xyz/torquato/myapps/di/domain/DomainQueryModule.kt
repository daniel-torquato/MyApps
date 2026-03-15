package xyz.torquato.myapps.di.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.torquato.myapps.api.web.IQueryRepository
import xyz.torquato.myapps.domain.web.SetQueryUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainQueryModule {

    @Singleton
    @Provides
    fun provideGetQueryUseCase(
        queryRepository: IQueryRepository,
    ): SetQueryUseCase = SetQueryUseCase(
        queryRepository
    )
}