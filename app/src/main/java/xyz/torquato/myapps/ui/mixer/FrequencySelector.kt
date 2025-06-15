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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.torquato.myapps.ui.components.AddButton
import xyz.torquato.myapps.ui.components.ElementButton
import xyz.torquato.myapps.ui.components.MusicalKeyboard
import xyz.torquato.myapps.ui.components.RangeSelector
import xyz.torquato.myapps.ui.components.ScaleButton
import xyz.torquato.myapps.ui.components.model.MyIcons
import xyz.torquato.myapps.ui.mixer.model.MixerUiState
import xyz.torquato.myapps.ui.mixer.model.Note
import xyz.torquato.myapps.ui.mixer.model.Tone
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
        onPause = viewModel::reset,
        onTouch = viewModel::play
    )
}


@Composable
fun FrequencySelectorProducer(
    onTrackPlay: (Track) -> Unit,
    onNotePlay: suspend (Note) -> Unit,
    onPause: () -> Unit,
    onTouch: (List<Tone>) -> Unit
) {

    var tones by remember { mutableStateOf(emptyList<Tone>()) }
    var uiState by remember { mutableStateOf(MixerUiState.Empty) }

    LaunchedEffect(tones, uiState.isTouching) {
        println("MyTag: Touching $tones")
        if (uiState.isTouching && !uiState.isEditing && tones.isNotEmpty()) {
            uiState = uiState.copy(currentTone = tones.first())
            onTouch(tones)
        } else if (uiState.isEditing && tones.isNotEmpty()) {
            val notes = uiState.track.notes.toMutableList()
            val noteIndex = uiState.editingIndex.first
            val toneIndex = uiState.editingIndex.second
            val note = notes.getOrNull(noteIndex)
            val oldTones = note?.tones?.toMutableList()
            val oldTone = oldTones?.getOrNull(toneIndex)
            val newTone = tones.firstOrNull()
            if (oldTone != null && newTone != null) {
                oldTones[toneIndex] = newTone
                notes[noteIndex] = note.copy(oldTones)
            }
            uiState = uiState.copy(
                track = uiState.track.copy(notes = notes),
                editingIndex = -1 to -1,
                isEditing = false
            )
        } else {
            onPause()
        }
    }

    LaunchedEffect(tones) {
        if (uiState.isRecording) {
            if (uiState.recordingIndex >= 0) {
                if (uiState.track.notes.isNotEmpty()) {
                    val oldTones = uiState.track.notes[uiState.recordingIndex].tones.toMutableList()
                    oldTones += tones
                    val notes = uiState.track.notes.toMutableList()
                    notes[uiState.recordingIndex] =
                        uiState.track.notes[uiState.recordingIndex].copy(
                            tones = oldTones
                        )
                    uiState = uiState.copy(
                        track = uiState.track.copy(notes = notes)
                    )
                }
                println("MyTag: Get Track ${uiState.recordingIndex}")
                uiState = uiState.copy(recordingIndex = -1)
            }
            uiState = uiState.copy(isRecording = false)
            println("MyTag: Points $tones")
        }
    }

    LaunchedEffect(uiState.isPlaying) {
        if (uiState.isPlaying) {
            uiState.track.notes.forEachIndexed { index, note ->
                uiState = uiState.copy(
                    playingIndex = index,
                    playingTones = note.tones
                )
                onNotePlay(note)
                println("MyTag: $index ${uiState.playingTones}")
                onPause()
            }
            uiState = uiState.copy(
                isPlaying = false,
                playingIndex = -1,
                playingTones = emptyList()
            )
        }
    }

    LaunchedEffect(uiState.isEditing, uiState.editingIndex) {
        println("MyTag: editing ${uiState.isEditing}")
        if (uiState.isEditing && uiState.track.notes.isNotEmpty()) {
            val note = uiState.track.notes.getOrNull(uiState.editingIndex.first)
            val tone = note?.tones?.getOrNull(uiState.editingIndex.second)
            println("MyTag; $tone")
            uiState = tone?.let {
                uiState.copy(
                    playingTones = listOf(it),
                    currentTone = it
                )
            } ?: uiState
        } else {
            uiState = uiState.copy(
                playingTones = emptyList()
            )
        }
    }

    Surface(
        modifier = Modifier
    ) {
        Column {
            RangeSelector(
                onTouchEvent = { uiState = uiState.copy(isTouching = it) },
                onInputTouch = { tones = it.toTones() },
                visualPoints = uiState.playingTones.map {
                    Offset(
                        ((it.frequency - 20) / (20000 - 20)),
                        1 - it.amplitude
                    )
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val scaleSize = 40
                val scaleModifier = Modifier.padding(2.dp).size(scaleSize.dp)
                ScaleButton(
                    modifier = scaleModifier,
                    onPress = {},
                    onRelease = {},
                    power = -3
                )
                ScaleButton(
                    modifier = scaleModifier,
                    onPress = {},
                    onRelease = {},
                    power = -2
                )
                ScaleButton(
                    modifier = scaleModifier,
                    onPress = {},
                    onRelease = {},
                    power = -1
                )
                ScaleButton(
                    modifier = scaleModifier,
                    onPress = {},
                    onRelease = {},
                    power = 0
                )
                ScaleButton(
                    modifier = scaleModifier,
                    onPress = {},
                    onRelease = {},
                    power = 1
                )
                ScaleButton(
                    modifier = scaleModifier,
                    onPress = {},
                    onRelease = {},
                    power = 2
                )
                ScaleButton(
                    modifier = scaleModifier,
                    onPress = {},
                    onRelease = {},
                    power = 3
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                MusicalKeyboard(
                    onPress = {
                        tones = listOf(Tone(amplitude = 0.8f, frequency = it.frequency))
                        uiState = uiState.copy(
                            isTouching = true,
                        )
                    },
                    onRelease = {
                        uiState = uiState.copy(
                            isTouching = false
                        )
                        tones = emptyList()
                    }
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                AvailableOptions(
                    isSaving = uiState.isSaving,
                    onSaving = { uiState = uiState.copy(isSaving = true) },
                    isEditing = uiState.isEditing,
                    onEditing = { uiState = uiState.copy(
                        playingTones = listOf(it)
                    ) },
                    tone = uiState.currentTone,
                    isRecording = uiState.isRecording,
                    onRecording = { uiState = uiState.copy(isRecording = true) },
                    onPlaying = { uiState = uiState.copy(isPlaying = true) },
                    isPlaying = uiState.isPlaying
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(3.dp)
                ) {
                    items(uiState.track.notes.size) { index ->
                        val duration = uiState.track.notes[index].duration.toFloat() / 1000f
                        val factor = duration / (1 + duration)
                        val height = 150.dp * factor
                        println("MyTag: composing $index ${uiState.playingIndex}")
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(height)
                                .background(if (uiState.playingIndex == index) Color.Red else Color.Transparent)
                                .border(1.dp, Color.Red),
                            contentPadding = PaddingValues(5.dp)
                        ) {
                            item {
                                AddButton(
                                    modifier = Modifier.height(height)
                                ) {
                                    println("MyTag: Recording $index")
                                    uiState =
                                        uiState.copy(isRecording = true, recordingIndex = index)
                                }
                            }
                            items(uiState.track.notes[index].tones.size) { subIndex ->
                                val red = 1 - uiState.track.notes[index].tones[subIndex].amplitude
                                val green = (uiState.track.notes[index].tones[subIndex].frequency - 20) / (20000 - 20)
                                Button(
                                    modifier = Modifier
                                        .padding(3.dp, 0.dp)
                                        .width(30.dp)
                                        .height(height),
                                    shape = RectangleShape,
                                    colors = ButtonColors(
                                        containerColor = Color(red, green, 0.0f, 1.0f),
                                        contentColor = Color(red, green, 0.0f, 1.0f),
                                        disabledContainerColor = Color.Transparent,
                                        disabledContentColor = Color.Transparent
                                    ),
                                    onClick = {
                                        println("MyTag: Editing $index $subIndex")
                                        uiState = uiState.copy(
                                            editingIndex = index to subIndex,
                                            isEditing = true
                                        )
                                    }
                                ) {}
                            }

                        }
                        Row {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(0.3f)
                                    .padding(10.dp)
                                    .height(30.dp),
                                text = "$duration"
                            )
                            Slider(
                                modifier = Modifier.fillMaxWidth(1f),
                                value = factor,
                                onValueChange = {
                                    val notes = uiState.track.notes.toMutableList()
                                    notes[index] = notes[index].copy(
                                        duration = (1000L * it / (1 - it)).toLong()
                                    )
                                    uiState = uiState.copy(
                                        track = uiState.track.copy(
                                            notes = notes
                                        )
                                    )
                                },
                                enabled = true,
                                valueRange = 0f..1f
                            )
                        }
                    }
                    item {
                        AddButton {
                            println("MyTag: Recording")
                            val notes = uiState.track.notes.toMutableList()
                            notes += Note.empty()
                            uiState = uiState.copy(
                                track = uiState.track.copy(
                                    notes = notes
                                ),
                                recordingIndex = notes.size - 1,
                                isRecording = true
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun AvailableOptions(
    isPlaying: Boolean,
    onPlaying: () -> Unit,
    isEditing: Boolean,
    onEditing: (Tone) -> Unit,
    tone: Tone,
    isSaving: Boolean,
    onSaving: () -> Unit,
    isRecording: Boolean,
    onRecording: () -> Unit
) {
    var frequencyText by remember { mutableStateOf<String>("${tone.frequency}") }
    var amplitudeText by remember { mutableStateOf<String>("${tone.amplitude}") }

    LaunchedEffect(tone) {
        println("MyTag: $tone")
        frequencyText = "${tone.frequency}"
        amplitudeText = "${tone.amplitude}"
    }

    LaunchedEffect(frequencyText, amplitudeText) {
        val freq = frequencyText.toFloatOrNull()
        val ampl = amplitudeText.toFloatOrNull()
        if (freq != null && ampl != null) {
            onEditing(Tone(freq, ampl))
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Row {
            ElementButton(
                onClick = onRecording,
                isWaiting = isRecording,
                icon = MyIcons.RECORD
            )
            ElementButton(
                onClick = onSaving,
                isWaiting = isSaving,
                icon = MyIcons.SAVE
            )
            ElementButton(
                onClick = onPlaying,
                isWaiting = isPlaying,
                icon = MyIcons.PLAY
            )
            ElementButton(
                onClick = {
                    println("MyTag: Click")
                },
                isWaiting = isEditing,
                icon = MyIcons.EDIT
            )
        }
        Column(
            modifier = Modifier.padding(end = 10.dp)
        ) {
            BasicTextField(
                value = "%.3f".format(amplitudeText.toFloatOrNull() ?: 0),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.width(100.dp),
                textStyle = LocalTextStyle.current.copy(
                    color = Color.White,
                    fontSize = 18.sp,
                    textAlign = TextAlign.End
                ),
                onValueChange = {
                    amplitudeText = it
                }
            )
            BasicTextField(
                value = "%.3f".format(frequencyText.toFloatOrNull() ?: 0),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.width(100.dp),
                textStyle = LocalTextStyle.current.copy(
                    color = Color.White,
                    fontSize = 18.sp,
                    textAlign = TextAlign.End
                ),
                onValueChange = {
                    frequencyText = it
                }
            )
        }
    }
}



