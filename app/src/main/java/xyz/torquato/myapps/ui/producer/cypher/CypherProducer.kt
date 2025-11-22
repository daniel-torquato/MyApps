package xyz.torquato.myapps.ui.producer.cypher

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.torquato.myapps.ui.component.cypher.model.CypherUiState


@Composable
fun CypherProducer(
    uiState: CypherUiState
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
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
    }
}

@Preview
@Composable
fun CypherPreview(
    uiState: CypherUiState = CypherUiState("example")
) {
    CypherProducer(uiState)
}