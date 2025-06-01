package xyz.torquato.myapps.data.track.datasource

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Track(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "raw_data") val rawData: String?,
)