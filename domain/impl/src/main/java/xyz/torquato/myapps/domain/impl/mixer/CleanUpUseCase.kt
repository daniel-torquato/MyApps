package xyz.torquato.myapps.domain.impl.mixer

import xyz.torquato.myapps.domain.api.sound.ISoundRepository

class CleanUpUseCase(
    private val repository: ISoundRepository
) {

    operator fun invoke() {
        repository.clear()
    }
}