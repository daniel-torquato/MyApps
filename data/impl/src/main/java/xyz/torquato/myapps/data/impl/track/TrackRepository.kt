package xyz.torquato.myapps.data.impl.track

import xyz.torquato.myapps.data.impl.track.external.Track
import xyz.torquato.myaps.domain.api.track.ITrackRepository
import javax.inject.Inject

class TrackRepository @Inject constructor(
    val dataSource: TrackDataSource
): ITrackRepository {

    override fun insert(id: Int, title: String?) {
        dataSource.insert(Track(id, title))
    }
}