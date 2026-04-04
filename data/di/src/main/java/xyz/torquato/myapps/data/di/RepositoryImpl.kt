package xyz.torquato.myapps.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.torquato.myapps.data.impl.paging.PagingRepository
import xyz.torquato.myaps.domain.api.pagging.IPagingRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryImpl {

    @Binds
    @Singleton
    abstract fun bindPagingImpl(impl: PagingRepository): IPagingRepository

}