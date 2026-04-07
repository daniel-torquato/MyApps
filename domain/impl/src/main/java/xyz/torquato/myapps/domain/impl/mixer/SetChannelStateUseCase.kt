package xyz.torquato.myapps.domain.impl.mixer

import xyz.torquato.myapps.domain.api.sound.ISoundRepository

class SetChannelStateUseCase(
    private val repository: ISoundRepository
) {

    operator fun invoke(isRunning: Boolean) {
        if (isRunning)
            repository.start()
        else
            repository.destroy()
    }
}