package xyz.torquato.myapps.ui.component.web

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import xyz.torquato.myapps.domain.web.GetSelectedBookUseCase
import xyz.torquato.myapps.domain.web.content.GetCacheBooksUseCase
import xyz.torquato.myapps.ui.component.web.model.BookMenuMapper.toUiState
import xyz.torquato.myapps.ui.component.web.model.BookMenuUiState
import javax.inject.Inject

@HiltViewModel
class WebFullDescriptionViewModel @Inject constructor(
    private val getWebCacheUseCase: GetCacheBooksUseCase,
    private val getSelectedBookUseCase: GetSelectedBookUseCase
): ViewModel() {

    val uiState: StateFlow<BookMenuUiState.BookItem> = combine(
        getBookContentMapped(),
        getSelectedBookUseCase().onEach {
            println("MyTag: SELECTED $it")
        }
    ) { books, selectedBookId ->
       val selectedBook = books.content.firstOrNull { it.id == selectedBookId }
        selectedBook
    }.filterNotNull().onEach {
        println("MyTag: New Ui State ${it}")
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = BookMenuUiState.BookItem.empty()
    )

    private fun getBookContentMapped() = getWebCacheUseCase().map { items ->
        println("MyTag: New Content $items")
        BookMenuUiState(items.toUiState())
    }



}