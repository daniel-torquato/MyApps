package xyz.torquato.myapps.ui.component.cypher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import xyz.torquato.myapps.api.sound.ICypherRepository
import xyz.torquato.myapps.data.cypher.CypherRepository
import xyz.torquato.myapps.ui.component.cypher.model.CypherUiState
import javax.inject.Inject

@HiltViewModel
class CypherViewModel @Inject constructor(
    private val repository: ICypherRepository
) : ViewModel() {

    private val useCase: Flow<CypherUiState> = flowOf(CypherUiState("dasdasd"))

    val uiState: StateFlow<CypherUiState> = useCase
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = CypherUiState("another")
        )

}