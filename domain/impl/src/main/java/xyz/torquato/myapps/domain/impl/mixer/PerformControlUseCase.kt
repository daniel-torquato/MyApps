package xyz.torquato.myapps.domain.impl.mixer

import xyz.torquato.myapps.domain.api.sound.ISoundRepository

class PerformControlUseCase(
    private val repository: ISoundRepository
) {

    operator fun invoke(enabled: Boolean) {
        repository.performControl(enabled)
    }
}