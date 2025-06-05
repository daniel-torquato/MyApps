package xyz.torquato.myapps.ui.mixer

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import xyz.torquato.myapps.ui.components.AddButton
import xyz.torquato.myapps.ui.components.RangeSelector
import xyz.torquato.myapps.ui.components.TextButton
import xyz.torquato.myapps.ui.mixer.model.InputTouch
import xyz.torquato.myapps.ui.mixer.model.MixerUiState
import xyz.torquato.myapps.ui.mixer.model.Note
import xyz.torquato.myapps.ui.mixer.model.Track

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ReturnFromAwaitPointerEventScope")
@Composable
fun FrequencySelector(
    viewModel: MixerViewModel
) {
    FrequencySelectorProducer(
        onTrackPlay = viewModel::play,
        onNotePlay = viewModel::play,
        onPause = viewModel::reset
    )
}


@Composable
fun FrequencySelectorProducer(
    onTrackPlay: (Track) -> Unit,
    onNotePlay: suspend (Note) -> Unit,
    onPause: () -> Unit
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    var points by remember { mutableStateOf(InputTouch.Companion.Zero) }

    var tmpPoints by remember { mutableStateOf(InputTouch.Companion.Zero) }

    var salvedPoints by remember { mutableStateOf(emptyList<InputTouch>()) }
    var salvedTimeFactors by remember { mutableStateOf(emptyList<Float>()) }

    var enabled by remember { mutableStateOf(false) }

    var uiState by remember { mutableStateOf(MixerUiState.Empty) }

    LaunchedEffect(points, enabled) {
        if (points.touchList.isNotEmpty() && enabled) {
            val note = points.toNote(300L)
            onNotePlay(note)
        } else {
            onPause()
        }
    }

    LaunchedEffect(points) {
        if (uiState.isRecording) {
            tmpPoints = points
            if (uiState.recordingIndex >= 0) {
                if (salvedPoints.isNotEmpty()) {
                    val buffer = salvedPoints[uiState.recordingIndex].touchList.toMutableList()
                    if (buffer.isNotEmpty()) {
                        buffer += points.touchList.first()
                        val tmpPoints = salvedPoints.toMutableList()
                        tmpPoints[uiState.recordingIndex] = tmpPoints[uiState.recordingIndex].copy(
                            touchList = buffer.toList()
                        )
                        salvedPoints = tmpPoints
                    }
                }
                uiState = uiState.copy(
                    track = salvedPoints.toNote(0, salvedTimeFactors.map { (1000L * it / (1 - it)).toLong() })
                )
                uiState = uiState.copy(recordingIndex = -1)
                println("MyTag: Get Track ${uiState.recordingIndex}")
            }
            uiState = uiState.copy(isRecording = false)
            println("MyTag: Points $points")
        }
    }

    LaunchedEffect(uiState.isSaving) {
        if (uiState.isSaving) {
            val oldPoints = salvedPoints.toMutableList()
            oldPoints += tmpPoints
            salvedPoints = oldPoints.toList()

            val oldDurations = salvedTimeFactors.toMutableList()
            oldDurations += (3f / 13f)
            salvedTimeFactors = oldDurations.toList()

            uiState = uiState.copy(
                track = salvedPoints.toNote(0L, salvedTimeFactors.map { (1000L * it / (1 - it)).toLong() })
            )
            uiState = uiState.copy(isSaving = false)
        }
    }

    LaunchedEffect(salvedTimeFactors) {
        salvedTimeFactors.forEachIndexed { index, timeFactor ->
            val track = uiState.track
            val notes = track.notes.toMutableList()
            notes[index] =
                notes[index].copy(duration = (1000L * timeFactor / (1 - timeFactor)).toLong())
            uiState = uiState.copy(
                track = track.copy(notes = notes)
            )
        }
    }

    LaunchedEffect(uiState.isPlaying) {
        if (uiState.isPlaying) {
            uiState.track.notes.forEachIndexed { index, note ->
                uiState = uiState.copy(playingIndex = index)
                onNotePlay(note)
                println("MyTag: $index")
                onPause()
            }
            uiState = uiState.copy(
                isPlaying = false,
                playingIndex = -1
            )
        }
    }


    Surface(
        modifier = Modifier
    ) {
        Column {
            RangeSelector(
                onTouchEvent = { enabled = it },
                onInputTouch = {
                    points = it
                },
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .onSizeChanged { size = it }
                    .background(Color.Blue)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = { uiState = uiState.copy(isRecording = true) },
                        containerColor = if (uiState.isRecording) Color.Red else Color.Green,
                        text = "Record"
                    )
                    TextButton(
                        onClick = { uiState = uiState.copy(isSaving = true) },
                        containerColor = if (uiState.isSaving) Color.Red else Color.Green,
                        text = "Save"
                    )
                    TextButton(
                        onClick = { uiState = uiState.copy(isPlaying = true) },
                        containerColor = if (uiState.isPlaying) Color.Blue else Color.Green,
                        text = "Play"
                    )
                }
                LazyRow(
                    modifier = Modifier.padding(10.dp)
                ) {
                    items(tmpPoints.touchList.size) { index ->
                        val red = (tmpPoints.touchList[index].x / tmpPoints.size.width).toFloat()
                        val green = (tmpPoints.touchList[index].y / tmpPoints.size.height).toFloat()
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color(red, green, 0.0f, 1.0f))
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(3.dp)
                ) {
                    items(uiState.track.notes.size) { index ->
                        println("MyTag: composing $index ${uiState.playingIndex}")
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(if (uiState.playingIndex == index) Color.Red else Color.Transparent)
                                .border(1.dp, Color.Red),
                            contentPadding = PaddingValues(5.dp)
                        ) {
                            items(uiState.track.notes[index].tones.size) { subIndex ->
                                val red = uiState.track.notes[index].tones[subIndex].amplitude
                                val green = uiState.track.notes[index].tones[subIndex].frequency / (20000 - 20)
                                Button(
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .size(40.dp),
                                    colors = ButtonColors(
                                        containerColor = Color(red, green, 0.0f, 1.0f),
                                        contentColor = Color(red, green, 0.0f, 1.0f),
                                        disabledContainerColor = Color.Transparent,
                                        disabledContentColor = Color.Transparent
                                    ),
                                    onClick = {}
                                ) {}
                            }
                            item {
                                AddButton {
                                    uiState = uiState.copy(isRecording = true, recordingIndex = index)
                                }
                            }
                        }
                        Row {
                            val duration = salvedTimeFactors[index]
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(0.3f)
                                    .padding(10.dp)
                                    .height(30.dp),
                                text = "${duration / (1 - duration)}"
                            )
                            Slider(
                                modifier = Modifier.fillMaxWidth(1f),
                                value = salvedTimeFactors[index],
                                onValueChange = {
                                    val durations = salvedTimeFactors.toMutableList()
                                    durations[index] = it
                                    salvedTimeFactors = durations.toList()
                                },
                                enabled = true,
                                valueRange = 0f..1f
                            )
                        }
                    }
                }
            }
        }
    }
}




