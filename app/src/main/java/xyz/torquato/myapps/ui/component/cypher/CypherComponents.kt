package xyz.torquato.myapps.ui.component.cypher

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import xyz.torquato.myapps.ui.producer.cypher.CypherProducer


@Composable
fun CypherComponent(
    viewModel: CypherViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CypherProducer(uiState)

}