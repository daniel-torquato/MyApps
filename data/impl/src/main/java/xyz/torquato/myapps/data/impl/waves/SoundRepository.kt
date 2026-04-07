package xyz.torquato.myapps.data.impl.waves

import xyz.torquato.myapps.domain.api.sound.ISoundRepository
import xyz.torquato.myapps.domain.api.sound.model.Tone
import javax.inject.Inject

class SoundRepository @Inject constructor() : ISoundRepository {

    external override fun setTone(frequency: Float, amplitude: Float)
    external override fun setTones(tones: Array<Tone>)
    external fun clean(size: Int)
    external override fun performControl(control: Boolean)
    private external fun startEngine()
    private external fun stopEngine()

    init {
        System.loadLibrary(LIB_NAME)
    }

    override fun clear() {
        clean(0)
    }

    override fun start() {
        startEngine()
    }

    override fun destroy() {
        stopEngine()
    }

    companion object {
        const val LIB_NAME: String = "waves"
    }
}