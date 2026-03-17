package xyz.torquato.myapps.api.web

import kotlinx.coroutines.flow.Flow

interface ILocalWebRepository {

    val selectedBookId: Flow<String>

    fun setSelectedBookId(id: String)
}