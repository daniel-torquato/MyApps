package xyz.torquato.myapps.ui.mixer

import xyz.torquato.myapps.ui.mixer.model.InputTouch
import xyz.torquato.myapps.ui.mixer.model.Note
import xyz.torquato.myapps.ui.mixer.model.Tone
import xyz.torquato.myapps.ui.mixer.model.Track

fun InputTouch.toNote(duration: Long): Note = Note(
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

fun List<InputTouch>.toNote(start: Long = 0L, durations: List<Long>): Track = Track(
    start = start,
    notes = mapIndexed { index, inputTouch -> inputTouch.toNote(durations[index]) }
)
