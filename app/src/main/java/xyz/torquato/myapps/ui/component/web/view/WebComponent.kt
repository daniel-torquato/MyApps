package xyz.torquato.myapps.ui.component.web.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.PIXEL_2
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import xyz.torquato.myapps.R
import xyz.torquato.myapps.ui.component.web.WebViewModel
import xyz.torquato.myapps.ui.component.web.model.BookMenuUiState
import xyz.torquato.myapps.ui.component.web.navigation.host.WebNavigator
import xyz.torquato.myapps.ui.component.web.navigation.host.WebRoute
import xyz.torquato.myapps.ui.component.web.view.WebPrev.Provider
import xyz.torquato.myapps.ui.theme.MyAppsTheme

object WebPrev {

    @Composable
    fun Builder(
        viewModelStoreOwner: ViewModelStoreOwner,
        router: Router
    ) = Component(viewModel = hiltViewModel(viewModelStoreOwner), router = router)

    @Composable
    fun Component(
        viewModel: WebViewModel,
        router: Router
    ) {
        val uiState by viewModel.uiState.collectAsState()

        Provider(uiState, viewModel::setQuery) { id ->
            println("Mytag: Book Selected $id")
            viewModel.selectBook(id)
            router.openDescription()
        }
    }

    class Router(
        private val controller: NavHostController
    ) : WebNavigator.Router(controller) {

        fun openDescription() {
            controller.navigate(WebRoute.FullDescription)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
    @Composable
    fun Provider(
        uiState: BookMenuUiState,
        onQuery: (String) -> Unit,
        onBookSelected: (String) -> Unit
    ) {
        var message by remember {
            mutableStateOf(
                TextFieldValue(
                    text = "",
                    selection = TextRange(0)
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = message,
                singleLine = true,
                label = {
                    Text(text = "Search")
                },
                textStyle = LocalTextStyle.current.copy(
                    color = Color.White,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                ),
                onValueChange = {
                    message = it
                    onQuery(it.text)
                }
            )

            Box {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize(),
                    columns = GridCells.Fixed(2)
                ) {
                    items(uiState.content, key = { it.id }) { bookItem ->
                        GlideImage(
                            modifier = Modifier
                                .padding(5.dp)
                                .clickable(onClick = { onBookSelected(bookItem.id) })
                                .fillMaxWidth(),
                            model = bookItem.smallThumbnailUrl.replace("http", "https"),
                            contentDescription = "Small Thumbnail",
                            contentScale = ContentScale.FillBounds,
                            failure = placeholder(R.drawable.ic_launcher_foreground)
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    device = PIXEL_2
)
@Composable
fun GreetingPreview() {
    MyAppsTheme {
        Provider(
            uiState = BookMenuUiState(
                listOf(
                    BookMenuUiState.BookItem(
                        id = "1",
                        title = "Title",
                        author = "",
                        description = "",
                        smallThumbnailUrl = "",
                        largeThumbnailUrl = "",
                        buyLink = null
                    ),
                    BookMenuUiState.BookItem(
                        id = "2",
                        title = "Title 2",
                        author = "",
                        description = "",
                        smallThumbnailUrl = "",
                        largeThumbnailUrl = "",
                        buyLink = null
                    )
                )
            ),
            onQuery = {},
            onBookSelected = {}
        )
    }
}