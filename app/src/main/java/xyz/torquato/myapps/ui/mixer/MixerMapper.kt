package xyz.torquato.myapps.ui.mixer

import xyz.torquato.myapps.ui.mixer.model.InputTouch
import xyz.torquato.myapps.ui.mixer.model.Note
import xyz.torquato.myapps.ui.mixer.model.Tone
import xyz.torquato.myapps.ui.mixer.model.Track

fun InputTouch.toTones(): List<Tone> = touchList.map { point ->
    val freqRange = (scale.endInclusive - scale.start)
    val freqStart = scale.start
    Tone(
        frequency = (point.x / size.width) * freqRange + freqStart,
        amplitude = (1 - point.y / size.height)
    )
}

fun InputTouch.toNote(duration: Long): Note = Note(
    tones = toTones(),
    duration = duration
)

fun List<InputTouch>.toNote(start: Long = 0L, durations: List<Long>): Track = Track(
    start = start,
    notes = mapIndexed { index, inputTouch -> inputTouch.toNote(durations[index]) }
)
