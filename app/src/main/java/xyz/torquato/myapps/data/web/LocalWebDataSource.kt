package xyz.torquato.myapps.data.web

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import xyz.torquato.myapps.api.web.model.QueryResult
import javax.inject.Inject

class LocalWebDataSource @Inject constructor() {

    private val _selectedBookId = MutableSharedFlow<String>(replay = 1, extraBufferCapacity = 1)

    val selectedBookId: Flow<String> = _selectedBookId.asSharedFlow()

    fun setSelectedBookId(id: String) {
        println("MyTag: SET SELECTED $id")
        _selectedBookId.tryEmit(id)
    }
}