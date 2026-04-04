package xyz.torquato.myapps.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.torquato.myapps.data.impl.cypher.CypherRepository
import xyz.torquato.myapps.data.impl.paging.PagingRepository
import xyz.torquato.myapps.data.impl.waves.SoundRepository
import xyz.torquato.myapps.data.impl.web.LocalWebRepository
import xyz.torquato.myapps.data.impl.web.QueryRepository
import xyz.torquato.myapps.data.impl.web.WebRepository
import xyz.torquato.myapps.data.impl.web.cache.WebCacheRepository
import xyz.torquato.myaps.domain.api.encrypt.ICypherRepository
import xyz.torquato.myaps.domain.api.pagging.IPagingRepository
import xyz.torquato.myaps.domain.api.sound.ISoundRepository
import xyz.torquato.myaps.domain.api.web.ILocalWebRepository
import xyz.torquato.myaps.domain.api.web.IQueryRepository
import xyz.torquato.myaps.domain.api.web.IWebRepository
import xyz.torquato.myaps.domain.api.web.cache.IWebCacheRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryImpl {

    @Binds
    @Singleton
    abstract fun bindWebImpl(impl: WebRepository): IWebRepository

    @Binds
    abstract fun bindImpl(impl: SoundRepository): ISoundRepository

    @Binds
    @Singleton
    abstract fun bindCypherImpl(impl: CypherRepository): ICypherRepository

    @Binds
    @Singleton
    abstract fun bindPagingImpl(impl: PagingRepository): IPagingRepository

    @Binds
    @Singleton
    abstract fun bindQueryImpl(impl: QueryRepository): IQueryRepository

    @Binds
    @Singleton
    abstract fun bindLocalWebImpl(impl: LocalWebRepository): ILocalWebRepository

    @Binds
    @Singleton
    abstract fun bindWebCacheImpl(impl: WebCacheRepository): IWebCacheRepository

}