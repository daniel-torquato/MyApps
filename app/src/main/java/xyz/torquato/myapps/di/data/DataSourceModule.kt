package xyz.torquato.myapps.di.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import xyz.torquato.myapps.data.track.datasource.TrackDatabase

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provides(
        @ApplicationContext appContext: Context
    ): RoomDatabase = Room.databaseBuilder(
        appContext,
        TrackDatabase::class.java, "raw-tracks"
    ).build()
}