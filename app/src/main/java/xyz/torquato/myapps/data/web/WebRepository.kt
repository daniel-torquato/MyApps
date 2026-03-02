package xyz.torquato.myapps.data.web

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import org.json.JSONException
import org.json.JSONObject
import xyz.torquato.myapps.api.web.IWebRepository
import xyz.torquato.myapps.api.web.model.QueryResult
import javax.inject.Inject

class WebRepository @Inject constructor() : IWebRepository {

    val data = MutableStateFlow<QueryResult>(QueryResult.Empty)

    override fun request(query: String): Boolean {
        return query.isEmpty()
    }

    fun setResult(input: JSONObject?, error: JSONException?) {
        error?.let {
            data.value = QueryResult.Error(error)
        } ?: input?.let {
            data.value = QueryResult.Valid(input)
        }
    }

    private external fun Search(query: String, startIndex: Int, length: Int)

    init {
        System.loadLibrary("web")
    }

}