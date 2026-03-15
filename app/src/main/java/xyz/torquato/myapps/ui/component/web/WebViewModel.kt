package xyz.torquato.myapps.ui.component.web

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import org.json.JSONArray
import org.json.JSONObject
import xyz.torquato.myapps.api.web.model.QueryResult
import xyz.torquato.myapps.domain.web.GetBooksUseCase
import xyz.torquato.myapps.domain.web.SetQueryUseCase
import xyz.torquato.myapps.ui.component.web.model.BookMenuUiState
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase,
    private val setQueryUseCase: SetQueryUseCase
): ViewModel() {

    val uiState: StateFlow<BookMenuUiState> = getBooksUseCase.invoke().map {
        println("MyTag: New Content ${it}")
        when(it) {
            is QueryResult.Valid -> {

                val pullString: JSONObject.(String) -> String = { id ->
                    runCatching { getString(id) }.getOrNull().orEmpty()
                }

                val fillString: JSONObject?.(String) -> String = { id ->
                    this?.pullString(id).orEmpty()
                }

                val pullObject: JSONObject.(String) -> JSONObject? = { id ->
                    runCatching { getJSONObject(id) }.getOrNull()
                }

                val pullArray: JSONObject.(String) -> JSONArray? = { id ->
                    runCatching { getJSONArray(id) }.getOrNull()
                }
                val result = mutableListOf<BookMenuUiState.BookItem>()

                val items = it.data.pullArray("items")
                if (items != null) {
                    repeat(items.length()) { index ->
                        val element = items.getJSONObject(index)


                        val volumeInfo = element.pullObject("volumeInfo")

                        val imageInfo = volumeInfo?.pullObject("imageLinks")

                        val saleInfo = element.pullObject("saleInfo")

                        val item = BookMenuUiState.BookItem(
                            id = element.pullString("id"),
                            title = volumeInfo.fillString("title"),
                            author = volumeInfo.fillString("authors"),
                            description = volumeInfo.fillString("description"),
                            smallThumbnailUrl = imageInfo.fillString("smallThumbnail"),
                            largeThumbnailUrl = imageInfo.fillString("thumbnail"),
                            buyLink = saleInfo.fillString("saleability")
                                .takeIf { saleability -> saleability == "FOR_SALE" }
                                .let { saleInfo.fillString("buyLink") }
                        )
                        result.add(item)
                    }
                }
                BookMenuUiState(result.toList())
            }
            else -> { BookMenuUiState(emptyList())}
        }
    }.onEach {
        println("MyTag: New Ui State ${it.content}")
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = BookMenuUiState(emptyList())
    )

    fun setQuery(query: String) = setQueryUseCase(query)
}