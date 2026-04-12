package xyz.torquato.myapps.test.microbench.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.torquato.myapps.domain.impl.web.SetQueryUseCase
import xyz.torquato.myapps.domain.impl.web.content.GetBooksUseCase
import xyz.torquato.myapps.domain.impl.web.content.SetSelectedBookUseCase
import xyz.torquato.myapps.domain.impl.web.paging.GetNextPageUseCase
import xyz.torquato.myapps.presentation.viewmodel.web.WebViewModel

@Module
@InstallIn(SingletonComponent::class)
object  ViewModelModule {

    @Provides
    fun provideWebViewModel(
        getBooksUseCase: GetBooksUseCase,
        setQueryUseCase: SetQueryUseCase,
        setSelectedBookUseCase: SetSelectedBookUseCase,
        getNextPageUseCase: GetNextPageUseCase
    ): WebViewModel = WebViewModel(
        getBooksUseCase,
        setQueryUseCase,
        setSelectedBookUseCase,
        getNextPageUseCase
    )
}