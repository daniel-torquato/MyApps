package xyz.torquato.myapps.ui.component.web.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import xyz.torquato.myapps.R
import xyz.torquato.myapps.ui.component.web.WebFullDescriptionViewModel
import xyz.torquato.myapps.ui.component.web.model.BookMenuUiState

object WebFullDescription {

    @Composable
    fun Builder(
        viewModelStoreOwner: ViewModelStoreOwner,
    ) = Component(viewModel = hiltViewModel(viewModelStoreOwner))


    @Composable
    fun Component(
        viewModel: WebFullDescriptionViewModel
    ) {
        val uiState by viewModel.uiState.collectAsState()

        Producer(uiState,{ _ ->})
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun Producer(
        uiState: BookMenuUiState.BookItem,
        onBookSelected: (String) -> Unit
    ) {
        Column {
            GlideImage(
                modifier = Modifier
                    .padding(5.dp)
                    .clickable(onClick = { onBookSelected(uiState.id) })
                    .fillMaxWidth(),
                model = uiState.largeThumbnailUrl.replace("http", "https"),
                contentDescription = "Small Thumbnail",
                contentScale = ContentScale.FillBounds,
                failure = placeholder(R.drawable.ic_launcher_foreground)
            )
        }
    }

}