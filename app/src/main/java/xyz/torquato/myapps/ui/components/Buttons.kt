package xyz.torquato.myapps.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import xyz.torquato.myapps.R

@Composable
fun AddButton(
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier
            .padding(5.dp)
            .size(40.dp)
            .background(Color(0.0f, 0.4f, 0.7f, 0.6f)),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier
                .padding(5.dp)
                .size(40.dp),
            painter = painterResource(R.drawable.add),
            contentDescription = null
        )
    }
}

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    containerColor: Color,
    text: String
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonColors(
            contentColor = Color.Transparent,
            containerColor = containerColor,
            disabledContainerColor = Color.Red,
            disabledContentColor = Color.Red
        )
    ) { Text(text, color = Color.Black) }
}