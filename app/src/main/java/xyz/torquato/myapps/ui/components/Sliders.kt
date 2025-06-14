package xyz.torquato.myapps.ui.components

import android.graphics.RuntimeShader
import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RangeSlider
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
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import org.intellij.lang.annotations.Language
import xyz.torquato.myapps.ui.mixer.model.InputTouch


@Language("AGSL")
val CUSTOM_SHADER = """
    const float M_PI = 3.1415926535897932384626433832795;
    
    uniform float2 resolution;
    uniform float2 center;
    uniform float start;
    
    half4 get_wave(half4 color, float2 uv) {
        float maxDimension = max(resolution.x, resolution.y);
        float factor = 0;
        float2 centerUV = center / maxDimension;
        float2 diff = centerUV - uv;
        float r = sqrt(diff.x * diff.x + diff.y * diff.y);
    
        factor = (1.0 + sin(r * 80 * M_PI)) / 2.0;
        
         if (r > start) {
            factor = 0;
         }
    
        return color; //+ half4(factor, factor, factor, 1.0);
    }
    
    half4 get_color(float r, float2 uv) {
        
        half4 color = half4(1.0 - uv.x, uv.y, uv.x, 1.0);
       
        return color;
    }
    
    half4 main(in float2 fragCoord) {
        float maxDimension = max(resolution.x, resolution.y);
        float2 uv = fragCoord / maxDimension;
        
        return get_wave(get_color(start, uv), uv);
    }
""".trimIndent()
@Composable
fun RangeSelector(
    onTouchEvent: (Boolean) -> Unit,
    onInputTouch: (InputTouch) -> Unit,
    visualPoints: List<Offset> = emptyList<Offset>()
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    var enabled by remember { mutableStateOf(false) }
    var input by remember { mutableIntStateOf(MotionEvent.ACTION_DOWN) }
    var sliderPosition by remember { mutableStateOf(20f..20000f) }
    var points by remember { mutableStateOf(InputTouch.Companion.Zero) }

    val shader = RuntimeShader(CUSTOM_SHADER)
    val shaderBrush = ShaderBrush(shader = shader)
    val animatedAlpha: Float by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.0f,
        animationSpec = tween(1000),
        label = "alpha"
    )

    LaunchedEffect(points) {
        onInputTouch(points)
    }

    LaunchedEffect(enabled) {
        onTouchEvent(enabled)
    }

    RangeSlider(
        value = sliderPosition,
        onValueChange = { sliderPosition = it },
        enabled = true,
        valueRange = 20f..20000f
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
                if (points.touchList.isNotEmpty()) {
                    shader.setFloatUniform(
                        "center",
                        points.touchList.first().x.toFloat(),
                        points.touchList.first().y.toFloat()
                    )
                } else {
                    shader.setFloatUniform("center", 0.0f, 0.0f)
                }
                shader.setFloatUniform("start", animatedAlpha)
                drawRect(brush = shaderBrush)
                drawPoints(
                    points = visualPoints.map { Offset(it.x * size.width, it.y * size.height) },
                    pointMode = PointMode.Points,
                    cap = StrokeCap.Round,
                    color = Color.Blue,
                    strokeWidth = 50f
                )
            }
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val offset = event.changes.first().position
                        points = InputTouch(
                            size = size,
                            touchList = event.changes.map { it.position },
                            scale = sliderPosition.start.toInt()..sliderPosition.endInclusive.toInt()
                        )

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

                        enabled = input == MotionEvent.ACTION_DOWN
                    }
                }
            },
    )
}