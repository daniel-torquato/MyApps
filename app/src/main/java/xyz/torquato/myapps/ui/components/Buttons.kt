package xyz.torquato.myapps.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import xyz.torquato.myapps.R
import xyz.torquato.myapps.ui.components.model.MyIcons

@Composable
fun AddButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier
            .background(Color(0.0f, 0.4f, 0.7f, 0.6f))
            .width(40.dp),
        onClick = onClick
    ) {
        Icon(
            modifier = modifier
                .width(40.dp),
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

@Composable
fun ElementButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isWaiting: Boolean,
    icon: MyIcons
) {
    val resourceId = when(icon) {
        MyIcons.EDIT -> R.drawable.pen_solid
        MyIcons.RECORD -> R.drawable.add
        MyIcons.SAVE -> R.drawable.floppy_disk_solid
        MyIcons.PLAY -> R.drawable.play_solid
    }

    Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .background(if (isWaiting) Color.Blue else Color.Transparent)
            .size(30.dp)
            .clip(RectangleShape)
            .paint(painterResource(resourceId), colorFilter = ColorFilter.tint(Color.White))
            .clickable(
                onClick = onClick,
                enabled = true,
                role = Role.Button,
                interactionSource = null,
                indication = ripple(true, 16.dp, Color.White)
            ),
        contentAlignment = Alignment.Center
    ) {}
}