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
import androidx.compose.runtime.mutableIntStateOf
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

    var isRecording by remember { mutableStateOf(false) }
    var recordingTrack by remember { mutableIntStateOf(-1) }
    var playingTrack by remember { mutableIntStateOf(-1) }
    var isSaving by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }

    var tmpPoints by remember { mutableStateOf(InputTouch.Companion.Zero) }

    var salvedPoints by remember { mutableStateOf(emptyList<InputTouch>()) }
    var salvedNotes by remember { mutableStateOf(Track.Empty) }
    var salvedTimeFactors by remember { mutableStateOf(emptyList<Float>()) }

    var enabled by remember { mutableStateOf(false) }

    LaunchedEffect(points, enabled) {
        if (points.touchList.isNotEmpty() && enabled) {
            val note = points.toNote(300L)
            onNotePlay(note)
        } else {
            onPause()
        }
    }

    LaunchedEffect(points) {
        if (isRecording) {
            tmpPoints = points
            if (recordingTrack >= 0) {
                if (salvedPoints.isNotEmpty()) {
                    val buffer = salvedPoints[recordingTrack].touchList.toMutableList()
                    if (buffer.isNotEmpty()) {
                        buffer += points.touchList.first()
                        val tmpPoints = salvedPoints.toMutableList()
                        tmpPoints[recordingTrack] = tmpPoints[recordingTrack].copy(
                            touchList = buffer.toList()
                        )
                        salvedPoints = tmpPoints
                    }
                }
                salvedNotes = salvedPoints.toNote(
                    0,
                    salvedTimeFactors.map { (1000L * it / (1 - it)).toLong() })
                recordingTrack = -1
                println("MyTag: Get Track $recordingTrack")
            }
            isRecording = false
            println("MyTag: Points $points")
        }
    }

    LaunchedEffect(isSaving) {
        if (isSaving) {
            val oldPoints = salvedPoints.toMutableList()
            oldPoints += tmpPoints
            salvedPoints = oldPoints.toList()

            val oldDurations = salvedTimeFactors.toMutableList()
            oldDurations += (3f / 13f)
            salvedTimeFactors = oldDurations.toList()

            salvedNotes =
                salvedPoints.toNote(0L, salvedTimeFactors.map { (1000L * it / (1 - it)).toLong() })
            isSaving = false
        }
    }

    LaunchedEffect(salvedTimeFactors) {
        salvedTimeFactors.forEachIndexed { index, timeFactor ->
            val notes = salvedNotes.notes.toMutableList()
            notes[index] =
                notes[index].copy(duration = (1000L * timeFactor / (1 - timeFactor)).toLong())
            salvedNotes = salvedNotes.copy(notes = notes.toList())
        }
    }

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            salvedNotes.notes.forEachIndexed { index, note ->
                playingTrack = index
                onNotePlay(note)
                println("MyTag: $index")
                onPause()
            }
            playingTrack = -1
            isPlaying = false
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
                        onClick = { isRecording = true },
                        containerColor = if (isRecording) Color.Red else Color.Green,
                        text = "Record"
                    )
                    TextButton(
                        onClick = { isSaving = true },
                        containerColor = if (isSaving) Color.Red else Color.Green,
                        text = "Save"
                    )
                    TextButton(
                        onClick = { isPlaying = true },
                        containerColor = if (isPlaying) Color.Blue else Color.Green,
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
                    items(salvedPoints.size) { index ->
                        println("MyTag: composing $index $playingTrack")
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(if (playingTrack == index) Color.Red else Color.Transparent)
                                .border(1.dp, Color.Red),
                            contentPadding = PaddingValues(5.dp)
                        ) {
                            items(salvedPoints[index].touchList.size) { subIndex ->
                                val red = salvedNotes.notes[index].tones[subIndex].amplitude
                                val green =
                                    salvedNotes.notes[index].tones[subIndex].frequency / (20000 - 20)
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
                                    isRecording = true
                                    recordingTrack = index
                                }
                            }
                        }
                        Row {
                            //var slider by remember { mutableFloatStateOf(0.5f) }
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




