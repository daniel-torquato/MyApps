package xyz.torquato.myapps.data.track.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TrackDao {
    @Query("SELECT * FROM track")
    fun getAll(): List<Track>

    @Query("SELECT * FROM track WHERE uid IN (:trackIds)")
    fun loadAllByIds(trackIds: IntArray): List<Track>

    @Query("SELECT * FROM track WHERE raw_data LIKE :first LIMIT 1")
    fun findByName(first: String): Track

    @Insert
    fun insertAll(vararg tracks: Track)

    @Delete
    fun delete(track: Track)
}