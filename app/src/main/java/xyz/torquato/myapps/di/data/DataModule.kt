package xyz.torquato.myapps.di.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.torquato.myapps.api.math.IMathRepository
import xyz.torquato.myapps.api.sound.ICypherRepository
import xyz.torquato.myapps.api.sound.ISoundRepository
import xyz.torquato.myapps.api.web.ILocalWebRepository
import xyz.torquato.myapps.api.web.IQueryRepository
import xyz.torquato.myapps.api.web.IWebRepository
import xyz.torquato.myapps.data.cypher.CypherRepository
import xyz.torquato.myapps.data.math.MathRepository
import xyz.torquato.myapps.data.waves.SoundRepository
import xyz.torquato.myapps.data.web.LocalWebRepository
import xyz.torquato.myapps.data.web.QueryRepository
import xyz.torquato.myapps.data.web.WebRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindImpl(impl: SoundRepository): ISoundRepository

    @Binds
    @Singleton
    abstract fun bindCypherImpl(impl: CypherRepository): ICypherRepository

    @Binds
    @Singleton
    abstract fun bindMathImpl(impl: MathRepository): IMathRepository

    @Binds
    @Singleton
    abstract fun bindWebImpl(impl: WebRepository): IWebRepository

    @Binds
    @Singleton
    abstract fun bindQueryImpl(impl: QueryRepository): IQueryRepository

    @Binds
    @Singleton
    abstract fun bindLocalWebImpl(impl: LocalWebRepository): ILocalWebRepository

}