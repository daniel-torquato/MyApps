package xyz.torquato.myapps.ui.producer.cypher

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.torquato.myapps.ui.component.cypher.model.CypherUiState


@Composable
fun CypherProducer(
    uiState: CypherUiState,
    onChangeToken: (String) -> Unit,
    onChangeMessage: (String) -> Unit,
) {

    var message by remember {
        mutableStateOf(
            TextFieldValue(
                text = "",
                selection = TextRange(0)
            )
        )
    }
    var token by remember {
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
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        BasicTextField(
            value = uiState.message,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            textStyle = LocalTextStyle.current.copy(
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            ),
            onValueChange = {}
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = token,
            label = {
              Text(text = "R")
            },
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = false,
                showKeyboardOnFocus = false
            ),
            onValueChange = {
                if (token.text != it.text) {
                    token = InputProcessing.convertToHex(it.text).let { newToken ->
                        TextFieldValue(
                            text = newToken,
                            selection = TextRange(newToken.length)
                        )
                    }
                    onChangeToken(InputProcessing.cleanUp(token.text))
                }
            },
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = message,
            label = {
                Text(text = "Message")
            },
            onValueChange = {
                if (message.text != it.text) {
                    message = InputProcessing.convertToHex(it.text).let { newMessage ->
                        TextFieldValue(
                            text = newMessage,
                            selection = TextRange(newMessage.length)
                        )
                    }
                    onChangeMessage(InputProcessing.cleanUp(message.text))
                }
            },
        )

        BasicTextField(
            modifier  = Modifier.padding(10.dp),
            value = uiState.message,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            textStyle = LocalTextStyle.current.copy(
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            ),
            onValueChange = {}
        )
    }
}

@Preview
@Composable
fun CypherPreview(
    uiState: CypherUiState = CypherUiState("example")
) {
    CypherProducer(
        uiState,
        onChangeToken = {},
        onChangeMessage = {}
    )
}