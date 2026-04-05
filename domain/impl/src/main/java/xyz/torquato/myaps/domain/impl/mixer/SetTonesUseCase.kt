package xyz.torquato.myaps.domain.impl.mixer

import xyz.torquato.myaps.domain.api.sound.ISoundRepository
import xyz.torquato.myaps.domain.impl.mixer.model.Tone
import xyz.torquato.myaps.domain.api.sound.model.Tone as DataTone

class SetTonesUseCase(
    private val repository: ISoundRepository
) {

    operator fun invoke(tones: Array<Tone>) {
        repository.setTones(tones.map { DataTone(it.frequency, it.amplitude) }.toTypedArray())
    }
}