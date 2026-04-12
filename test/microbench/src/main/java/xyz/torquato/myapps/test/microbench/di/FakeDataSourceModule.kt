package xyz.torquato.myapps.test.microbench.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.torquato.myapps.data.impl.web.WebDataSource
import xyz.torquato.myapps.test.microbench.fakes.FakeWebDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FakeDataSourceModule {


    @Provides
    @Singleton
    fun providePagingDataSource(): WebDataSource = FakeWebDataSource()
}