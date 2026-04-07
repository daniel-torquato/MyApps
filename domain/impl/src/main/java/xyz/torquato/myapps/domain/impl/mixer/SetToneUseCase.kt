package xyz.torquato.myapps.domain.impl.mixer

import xyz.torquato.myapps.domain.api.sound.ISoundRepository


class SetToneUseCase(
    private val repository: ISoundRepository
) {

    operator fun invoke(frequency: Float, amplitude: Float) {
        repository.setTone(frequency, amplitude)
    }
}