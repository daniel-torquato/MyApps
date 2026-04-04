package xyz.torquato.myaps.domain.api.track

interface ITrackRepository {

    fun insert(id: Int, title: String?)
}