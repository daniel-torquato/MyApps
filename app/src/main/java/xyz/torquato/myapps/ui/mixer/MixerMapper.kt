package xyz.torquato.myapps.ui.mixer

import xyz.torquato.myapps.ui.mixer.model.InputTouch
import xyz.torquato.myapps.ui.mixer.model.Note
import xyz.torquato.myapps.ui.mixer.model.Tone

fun InputTouch.toNote(duration: Long): Note = Note(
    start = 0,
    tones = touchList.map { point ->
        val freqRange = (scale.endInclusive - scale.start)
        val freqStart = scale.start
        Tone(
            frequency = (point.y / size.height) * freqRange + freqStart,
            amplitude = (point.x / size.width)
        )
    },
    duration = duration
)

fun List<InputTouch>.toNote(duration: Long): List<Note> = map { it.toNote(duration) }

fun List<InputTouch>.toNote(durations: List<Long>): List<Note> = mapIndexed { index, inputTouch -> inputTouch.toNote(durations[index]) }