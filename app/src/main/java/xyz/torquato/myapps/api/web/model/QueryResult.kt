package xyz.torquato.myapps.api.web.model

import org.json.JSONException
import org.json.JSONObject

sealed interface QueryResult {

    data object Empty: QueryResult

    data class Valid(val data: JSONObject): QueryResult

    data class Error(val error: JSONException): QueryResult
}