package xyz.torquato.myapps.ui.component.cypher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import xyz.torquato.myapps.ui.component.cypher.model.CypherUiState
import xyz.torquato.myapps.domain.impl.cypher.GetAuthenticationUseCase
import xyz.torquato.myapps.domain.impl.cypher.SetMessageUseCase
import xyz.torquato.myapps.domain.impl.cypher.SetTokenUseCase
import javax.inject.Inject

@HiltViewModel
class CypherViewModel @Inject constructor(
    private val getAuthenticationUseCase: GetAuthenticationUseCase,
    private val setTokenUseCase: SetTokenUseCase,
    private val setMessageUseCase: SetMessageUseCase
) : ViewModel() {

    val uiState: StateFlow<CypherUiState> = getAuthenticationUseCase().map {
        CypherUiState(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = CypherUiState("another")
    )

    fun changeToken(newToken: String) {
        setTokenUseCase(newToken)
    }

    fun changeMessage(newMessage: String) {
        setMessageUseCase(newMessage)
    }


}