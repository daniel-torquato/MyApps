package com.example.mywaves.ui.views

import android.annotation.SuppressLint
import android.view.MotionEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@SuppressLint("ReturnFromAwaitPointerEventScope")
@Composable
fun FrequencySelector(
    onTouch: (action: Int, frequency: Float, amplitude: Float) -> Unit = {_, _, _ -> }
) {
    // A surface container using the 'background' color from the theme
    var log by remember { mutableStateOf("") }
    var input by remember { mutableIntStateOf(MotionEvent.ACTION_DOWN) }
    var size by remember { mutableStateOf(IntSize.Zero) }
    var color by remember { mutableStateOf(Color.Blue) }
    var sliderPosition by remember { mutableStateOf(20f .. 20000f) }
    Surface(
        modifier = Modifier
            .padding(40.dp),
        color = Color.Green
    ) {
        Column() {
            RangeSlider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                enabled = true,
                valueRange = 20f .. 20000f
            )
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize()
                    .onSizeChanged { size = it }
                    .background(color = color)
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                val event = awaitPointerEvent()
                                val offset = event.changes.first().position
                                val freq = (offset.y / size.height) * (sliderPosition.endInclusive - sliderPosition.start) + sliderPosition.start
                                val amplitude = (offset.x / size.width)
                                log =
                                    "${event.type}, ${offset}, ($freq, $amplitude), ${size}, $sliderPosition"


                                input = when (event.type) {
                                    PointerEventType.Press -> MotionEvent.ACTION_DOWN

                                    PointerEventType.Release -> MotionEvent.ACTION_UP

                                    PointerEventType.Exit -> MotionEvent.ACTION_UP

                                    PointerEventType.Enter -> MotionEvent.ACTION_DOWN

                                    PointerEventType.Move -> {
                                        if ((0 < offset.x && offset.x < size.width) and (0 < offset.y && offset.y < size.height))
                                            MotionEvent.ACTION_DOWN
                                        else
                                            MotionEvent.ACTION_UP
                                    }

                                    else -> MotionEvent.ACTION_CANCEL
                                }

                                color =
                                    if ((0 < offset.x && offset.x < size.width) and (0 < offset.y && offset.y < size.height))
                                        Color.hsv(
                                            360 * (offset.y / size.height),
                                            (offset.x / size.width),
                                            1f,
                                            1f
                                        )
                                    else
                                        Color.Black

                                onTouch(
                                    input,
                                    freq,
                                    amplitude
                                )
                            }
                        }
                    },
            ) {
                Text(
                    text = log,
                )
            }
        }
    }
}