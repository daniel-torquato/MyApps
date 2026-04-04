package xyz.torquato.myapps.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.torquato.myapps.data.impl.cypher.CypherRepository
import xyz.torquato.myapps.data.impl.paging.PagingRepository
import xyz.torquato.myapps.data.impl.waves.SoundRepository
import xyz.torquato.myaps.domain.api.encrypt.ICypherRepository
import xyz.torquato.myaps.domain.api.pagging.IPagingRepository
import xyz.torquato.myaps.domain.api.sound.ISoundRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryImpl {

    @Binds
    abstract fun bindImpl(impl: SoundRepository): ISoundRepository

    @Binds
    @Singleton
    abstract fun bindCypherImpl(impl: CypherRepository): ICypherRepository

    @Binds
    @Singleton
    abstract fun bindPagingImpl(impl: PagingRepository): IPagingRepository

}