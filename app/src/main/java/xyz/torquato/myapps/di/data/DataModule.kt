package xyz.torquato.myapps.di.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.torquato.myapps.api.math.IMathRepository
import xyz.torquato.myapps.data.math.MathRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMathImpl(impl: MathRepository): IMathRepository









}