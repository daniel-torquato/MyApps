package xyz.torquato.myapps.presentation.viewmodel.mixer

import xyz.torquato.myapps.presentation.viewmodel.mixer.model.InputTouch
import xyz.torquato.myapps.presentation.viewmodel.mixer.model.Note
import xyz.torquato.myapps.domain.impl.mixer.model.Tone as DomainTone
import xyz.torquato.myapps.presentation.viewmodel.mixer.model.Tone
import xyz.torquato.myapps.presentation.viewmodel.mixer.model.Track

fun InputTouch.toTones(): List<Tone> = touchList.map { point ->
    val freqRange = (scale.endInclusive - scale.start)
    val freqStart = scale.start
    Tone(
        frequency = (point.x / size.width) * freqRange + freqStart,
        amplitude = (1 - point.y / size.height)
    )
}

fun Tone.toDomain(): DomainTone = DomainTone(
    frequency = frequency,
    amplitude = amplitude
)

fun List<Tone>.toDomain(): List<DomainTone> = map { it.toDomain() }