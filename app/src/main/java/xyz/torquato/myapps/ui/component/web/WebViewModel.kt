package xyz.torquato.myapps.ui.component.web

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import xyz.torquato.myapps.domain.web.SetQueryUseCase
import xyz.torquato.myapps.domain.web.content.GetBooksUseCase
import xyz.torquato.myapps.ui.component.web.model.BookMenuMapper.toUiState
import xyz.torquato.myapps.ui.component.web.model.BookMenuUiState
import xyz.torquato.myaps.domain.impl.web.content.SetSelectedBookUseCase
import xyz.torquato.myaps.domain.impl.web.paging.GetNextPageUseCase
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase,
    private val setQueryUseCase: SetQueryUseCase,
    private val setSelectedBookUseCase: SetSelectedBookUseCase,
    private val getNextPageUseCase: GetNextPageUseCase
): ViewModel() {

    val uiState: StateFlow<BookMenuUiState> = getBooksUseCase.invoke().map { items ->
        println("MyTag: New Content ${items}")
        BookMenuUiState(items.toUiState())
    }.onEach {
        println("MyTag: New Ui State ${it.content}")
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = BookMenuUiState(emptyList())
    )

    fun setQuery(query: String) = setQueryUseCase(query)

    fun selectBook(id: String) = setSelectedBookUseCase(id)

    fun getMoreItems() {
        println("MyTag: [VM] GET MORE ITEMS")
        getNextPageUseCase()
    }
}