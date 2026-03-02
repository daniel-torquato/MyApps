package xyz.torquato.myapps.di.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.torquato.myapps.api.math.IMathRepository
import xyz.torquato.myapps.api.sound.ICypherRepository
import xyz.torquato.myapps.api.sound.ISoundRepository
import xyz.torquato.myapps.data.cypher.CypherRepository
import xyz.torquato.myapps.data.math.MathRepository
import xyz.torquato.myapps.data.waves.SoundRepository
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


}