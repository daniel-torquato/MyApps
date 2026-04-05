package xyz.torquato.myapps.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.torquato.myaps.domain.api.web.ILocalWebRepository
import xyz.torquato.myaps.domain.impl.web.content.SetSelectedBookUseCase
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
}