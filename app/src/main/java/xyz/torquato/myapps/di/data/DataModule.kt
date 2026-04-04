package xyz.torquato.myapps.di.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.torquato.myapps.api.math.IMathRepository
import xyz.torquato.myapps.api.web.ILocalWebRepository
import xyz.torquato.myapps.api.web.IQueryRepository
import xyz.torquato.myapps.api.web.IWebRepository
import xyz.torquato.myapps.api.web.cache.IWebCacheRepository
import xyz.torquato.myapps.data.math.MathRepository
import xyz.torquato.myapps.data.web.LocalWebRepository
import xyz.torquato.myapps.data.web.QueryRepository
import xyz.torquato.myapps.data.web.WebRepository
import xyz.torquato.myapps.data.web.cache.WebCacheRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMathImpl(impl: MathRepository): IMathRepository

    @Binds
    @Singleton
    abstract fun bindWebImpl(impl: WebRepository): IWebRepository

    @Binds
    @Singleton
    abstract fun bindWebCacheImpl(impl: WebCacheRepository): IWebCacheRepository

    @Binds
    @Singleton
    abstract fun bindQueryImpl(impl: QueryRepository): IQueryRepository

    @Binds
    @Singleton
    abstract fun bindLocalWebImpl(impl: LocalWebRepository): ILocalWebRepository

}