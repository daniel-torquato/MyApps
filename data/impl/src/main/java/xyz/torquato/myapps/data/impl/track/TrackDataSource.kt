package xyz.torquato.myapps.data.impl.track

import android.content.Context
import androidx.room.Room
import dagger.hilt.android.qualifiers.ApplicationContext
import xyz.torquato.myapps.data.impl.track.external.Track
import xyz.torquato.myapps.data.impl.track.external.TrackDatabase
import javax.inject.Inject

class TrackDataSource @Inject constructor(
    @param:ApplicationContext private val appContext: Context
) {
    private val dataBase = Room.databaseBuilder(
        appContext,
        TrackDatabase::class.java, "raw-tracks"
    ).build()

    private val dao = dataBase.trackDao()

    fun insert(track: Track) {
        dataBase.trackDao().insertAll(track)
    }
}