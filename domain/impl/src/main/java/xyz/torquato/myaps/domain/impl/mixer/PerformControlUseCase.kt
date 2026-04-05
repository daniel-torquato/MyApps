package xyz.torquato.myaps.domain.impl.mixer

import xyz.torquato.myaps.domain.api.sound.ISoundRepository

class PerformControlUseCase(
    private val repository: ISoundRepository
) {

    operator fun invoke(enabled: Boolean) {
        repository.performControl(enabled)
    }
}