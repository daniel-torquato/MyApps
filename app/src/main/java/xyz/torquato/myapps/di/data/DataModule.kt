package xyz.torquato.myapps.di.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.torquato.myapps.api.sound.ISoundRepository
import xyz.torquato.myapps.data.waves.SoundRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindImpl(impl: SoundRepository): ISoundRepository

}