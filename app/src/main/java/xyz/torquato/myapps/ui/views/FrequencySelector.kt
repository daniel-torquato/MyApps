package xyz.torquato.myapps.ui.views

import android.annotation.SuppressLint
import android.graphics.RuntimeShader
import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.intellij.lang.annotations.Language

@Language("AGSL")
val CUSTOM_SHADER = """
    const float M_PI = 3.1415926535897932384626433832795;
    
    uniform float2 resolution;
    uniform float2 center;
    uniform float start;
    
    half4 main(in float2 fragCoord) {
        float maxDimension = max(resolution.x, resolution.y);
        float2 uv = fragCoord/maxDimension;
        
        float factor = 0;
        float2 centerUV = center / maxDimension;
        float2 diff = centerUV - uv;
        float r = sqrt(diff.x * diff.x + diff.y * diff.y);
        factor = (1.0 + sin(r * 80 * M_PI)) / 2.0;
        
        if (r > start)
            factor = 0;
        
        return half4(uv.x, uv.y, factor, 1.0);
    }
""".trimIndent()

@SuppressLint("ReturnFromAwaitPointerEventScope")
@Composable
fun FrequencySelector(
    onTouch: (action: Int, frequency: Float, amplitude: Float) -> Unit = {_, _, _ -> },
) {
    // A surface container using the 'background' color from the theme
    var log by remember { mutableStateOf("") }
    var input by remember { mutableIntStateOf(MotionEvent.ACTION_DOWN) }
    var size by remember { mutableStateOf(IntSize.Zero) }
    var color by remember { mutableStateOf(Color.Blue) }
    var sliderPosition by remember { mutableStateOf(20f .. 20000f) }
    var points by remember { mutableStateOf(emptyList<Offset>()) }

    var isRecording by remember { mutableStateOf(false) }
    var isSalving by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }
    var selectedPoints by remember { mutableStateOf(emptyList<Offset>()) }
    var salvedPoints by remember { mutableStateOf(emptyList<List<Offset>>()) }

    var enabled by remember { mutableStateOf(false) }
    val animatedAlpha: Float by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.0f,
        animationSpec = tween(1000),
        label = "alpha"
    )
    val shader = RuntimeShader(CUSTOM_SHADER)
    val shaderBrush = ShaderBrush(shader = shader)

    LaunchedEffect(isRecording) {
        println("MyTag: Example $isRecording")
    }

    LaunchedEffect(points) {
        if (isRecording) {
            selectedPoints = points
            isRecording = false
            println("MyTag: Points $points")
        }
    }

    LaunchedEffect(selectedPoints) {
        if (selectedPoints.isNotEmpty()) {
            println("MyTag: SALVED $selectedPoints")
        }
    }

    LaunchedEffect(isSalving) {
        if (isSalving) {
            val buffer = salvedPoints.toMutableList()
            val newItems = selectedPoints.toMutableList()
            buffer += newItems
            salvedPoints = buffer.toList()
            isSalving = false
        }
    }

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            salvedPoints.forEach { notes ->
                notes.forEach { tone ->
                    val freq =
                        (tone.y / size.height) * (sliderPosition.endInclusive - sliderPosition.start) + sliderPosition.start
                    val amplitude = (tone.x / size.width)
                    onTouch(0, freq, amplitude)
                    delay(300L)
                }
                onTouch(1, 0.0f, 0.0f)
            }
            isPlaying = false
        }
    }

    Surface(
        modifier = Modifier
    ) {
        Column {
            RangeSlider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                enabled = true,
                valueRange = 20f .. 20000f
            )
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .onSizeChanged { size = it }
                    .drawBehind {
                        shader.setFloatUniform(
                            "resolution",
                            size.width.toFloat(),
                            size.height.toFloat()
                        )
                        if (points.isNotEmpty()) {
                            shader.setFloatUniform(
                                "center",
                                points.first().x.toFloat(),
                                points.first().y.toFloat()
                            )
                        } else {
                            shader.setFloatUniform("center", 0.0f, 0.0f)
                        }
                        shader.setFloatUniform("start", animatedAlpha)
                        drawRect(brush = shaderBrush)
                    }
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                val event = awaitPointerEvent()
                                val offset = event.changes.first().position
                                points = event.changes.map { it.position }
                                val freq =
                                    (offset.y / size.height) * (sliderPosition.endInclusive - sliderPosition.start) + sliderPosition.start
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

                                if (input == MotionEvent.ACTION_DOWN)
                                    enabled = true
                                else
                                    enabled = false

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
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .onSizeChanged { size = it }
                    .background(Color.Blue)
            ) {
                Row {
                    Button(
                        modifier = Modifier
                            .width(40.dp)
                            .aspectRatio(1f),
                        onClick = {
                            isRecording = true
                        },
                        colors = ButtonColors(
                            contentColor = Color.Transparent,
                            containerColor = if (isRecording) Color.Red else Color.Green,
                            disabledContainerColor = Color.Red,
                            disabledContentColor = Color.Red
                        )
                    ) {}
                    Button(
                        modifier = Modifier
                            .width(40.dp)
                            .aspectRatio(1f),
                        onClick = {
                            isSalving = true
                        },
                        colors = ButtonColors(
                            contentColor = Color.Transparent,
                            containerColor = if (isSalving) Color.Red else Color.Green,
                            disabledContainerColor = Color.Red,
                            disabledContentColor = Color.Red
                        )
                    ) {}

                    Button(
                        modifier = Modifier
                            .width(40.dp)
                            .aspectRatio(1f),
                        onClick = {
                            isPlaying = true
                        },
                        colors = ButtonColors(
                            contentColor = Color.Transparent,
                            containerColor = if (isPlaying) Color.Blue else Color.Green,
                            disabledContainerColor = Color.Red,
                            disabledContentColor = Color.Red
                        )
                    ) {}
                }
                LazyRow(
                    modifier = Modifier.padding(10.dp)
                ) {
                    items(selectedPoints.size) { index ->
                        val red = selectedPoints[index].x / size.width
                        val green = selectedPoints[index].y / size.height
                        Box(modifier = Modifier
                            .size(40.dp)
                            .background(Color(red, green, 0.0f, 1.0f)))
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(3.dp)
                ) {
                    items(salvedPoints.size) { index ->
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, Color.Red),
                            contentPadding = PaddingValues(5.dp)
                        ) {
                            items(salvedPoints[index].size) { subIndex ->
                                val red = salvedPoints[index][subIndex].x / size.width
                                val green = salvedPoints[index][subIndex].y / size.height
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(Color(red, green, 0.0f, 1.0f))
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

