package xyz.torquato.myaps.domain.impl.mixer

import xyz.torquato.myaps.domain.api.sound.ISoundRepository

class CleanUpUseCase(
    private val repository: ISoundRepository
) {

    operator fun invoke() {
        repository.clear()
    }
}