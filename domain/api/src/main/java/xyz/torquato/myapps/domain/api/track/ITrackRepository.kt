package xyz.torquato.myapps.domain.api.track

interface ITrackRepository {

    fun insert(id: Int, title: String?)
}