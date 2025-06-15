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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.torquato.myapps.R
import xyz.torquato.myapps.ui.components.model.MidiNote
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


@Composable
fun ToneButton(
    modifier: Modifier = Modifier,
    onPress: (MidiNote) -> Unit,
    onRelease: () -> Unit,
    note: MidiNote
) {
    val factor = (note.frequency - 20.0f) / (20000.0f - 20.0f)
    val color = Color(factor, 0.0f, 1f - factor)
    val textMeasurer = rememberTextMeasurer(cacheSize = 1)
    val fontSize = 16
    var clickState by remember { mutableStateOf(false) }

    LaunchedEffect(clickState) {
        println("MyTag: $clickState")
        if (clickState)
            onPress(note)
        else
            onRelease()
    }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val offset = event.changes.first().position

                        clickState = when (event.type) {
                            PointerEventType.Enter,
                            PointerEventType.Press -> true

                            PointerEventType.Move ->
                                (0 < offset.x && offset.x < size.width) and (0 < offset.y && offset.y < size.height)

                            else -> false
                        }
                    }
                }
            }
            .drawBehind {
                drawPath(
                    path = Path().apply {
                        val yFactor = 1f / 4f
                        val xFactor = 1f / 2f
                        moveTo(0f, size.height)
                        lineTo(size.width, size.height)
                        lineTo(size.width, size.height * yFactor)
                        lineTo(size.width * xFactor, 0f)
                        lineTo(0f, size.height * yFactor)
                        lineTo(0f, size.height)
                    },
                    color = Color.White
                )
                drawPath(
                    path = Path().apply {
                        val yFactor = 1f / 4f
                        val xFactor = 1f / 2f
                        moveTo(0f, size.height)
                        lineTo(size.width, size.height)
                        lineTo(size.width, size.height * yFactor)
                        lineTo(size.width * xFactor, 0f)
                        lineTo(0f, size.height * yFactor)
                        lineTo(0f, size.height)
                    },
                    style = Stroke(width = 6f),
                    color = Color.DarkGray
                )
                drawText(
                    textMeasurer = textMeasurer,
                    text = note.note,
                    topLeft = Offset(center.x - (fontSize), center.y + fontSize),
                    style = TextStyle(
                        fontSize = fontSize.sp,
                        color = Color.Black
                    )
                )
            },
        contentAlignment = Alignment.Center
    ) {}
}

@Composable
fun SemiToneButton(
    modifier: Modifier = Modifier,
    onPress: (MidiNote) -> Unit,
    onRelease: () -> Unit,
    yFactor: Float,
    note: MidiNote
) {
    var clickState by remember { mutableStateOf(false) }
    val textMeasurer = rememberTextMeasurer(cacheSize = 1)
    val fontSize = 16

    LaunchedEffect(clickState) {
        if (clickState)
            onPress(note)
        else
            onRelease()
    }

    Box(
        modifier = modifier
            .drawBehind {
                drawPath(
                    Path().apply {
                        moveTo(0f, 0f)
                        lineTo(0f, size.height * yFactor)
                        lineTo(size.width / 2, size.height)
                        lineTo(size.width, size.height * yFactor)
                        lineTo(size.width, 0f)
                    },
                    color = Color.Black
                )
                drawPath(
                    Path().apply {
                        moveTo(0f, 0f)
                        lineTo(0f, size.height * yFactor)
                        lineTo(size.width / 2, size.height)
                        lineTo(size.width, size.height * yFactor)
                        lineTo(size.width, 0f)
                    },
                    style = Stroke(
                        width = 6f
                    ),
                    color = Color.DarkGray
                )
                drawText(
                    textMeasurer = textMeasurer,
                    text = note.note,
                    topLeft = Offset(center.x - (2 * fontSize), fontSize.toFloat()),
                    style = TextStyle(
                        fontSize = fontSize.sp,
                        color = Color.White
                    )
                )
            }
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val offset = event.changes.first().position

                        clickState = when (event.type) {
                            PointerEventType.Enter,
                            PointerEventType.Press -> true

                            PointerEventType.Move ->
                                (0 < offset.x && offset.x < size.width) and (0 < offset.y && offset.y < size.height)

                            else -> false
                        }
                    }
                }
            }
        ,
        contentAlignment = Alignment.Center
    ) {
       // Text(text = note.note)
    }
}

@Composable
fun ScaleButton(
    modifier: Modifier = Modifier,
    onPress: (Int) -> Unit,
    onRelease: () -> Unit,
    power: Int
) {
    var clickState by remember { mutableStateOf(false) }
    val textMeasurer = rememberTextMeasurer(cacheSize = 1)
    val fontSize = 16

    LaunchedEffect(clickState) {
        if (clickState)
            onPress(power)
        else
            onRelease()
    }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.DarkGray)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val offset = event.changes.first().position

                        clickState = when (event.type) {
                            PointerEventType.Enter,
                            PointerEventType.Press -> true

                            PointerEventType.Move ->
                                (0 < offset.x && offset.x < size.width) and (0 < offset.y && offset.y < size.height)

                            else -> false
                        }
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(text = "$power")
    }
}

