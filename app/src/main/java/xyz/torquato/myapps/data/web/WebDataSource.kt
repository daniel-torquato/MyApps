package xyz.torquato.myapps.data.web

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import xyz.torquato.myapps.api.web.model.QueryResult
import javax.inject.Inject

class WebDataSource @Inject constructor() {

    external fun search(query: String, start: Int, length: Int): Any

    init {
        System.loadLibrary("web")
    }
}