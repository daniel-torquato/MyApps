package xyz.torquato.myapps.ui.component.cypher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import xyz.torquato.myaps.domain.api.encrypt.ICypherRepository
import xyz.torquato.myapps.ui.component.cypher.model.CypherUiState
import javax.inject.Inject

@HiltViewModel
class CypherViewModel @Inject constructor(
    private val repository: ICypherRepository
) : ViewModel() {

    val uiState: StateFlow<CypherUiState> = repository.message.map {
        CypherUiState(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = CypherUiState("another")
    )

    fun changeToken(newToken: String) {
        repository.setToken(newToken)
    }

    fun changeMessage(newMessage: String) {
        repository.setMessage(newMessage)
    }


}