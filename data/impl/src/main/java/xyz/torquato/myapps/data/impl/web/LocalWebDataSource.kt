package xyz.torquato.myapps.data.impl.web

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class LocalWebDataSource @Inject constructor() {

    private val _selectedBookId = MutableSharedFlow<String>(replay = 1, extraBufferCapacity = 1)

    val selectedBookId: Flow<String> = _selectedBookId.asSharedFlow()

    fun setSelectedBookId(id: String) {
        println("MyTag: SET SELECTED $id")
        _selectedBookId.tryEmit(id)
    }
}