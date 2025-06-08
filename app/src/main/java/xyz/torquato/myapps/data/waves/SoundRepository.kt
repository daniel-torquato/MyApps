package xyz.torquato.myapps.data.waves

import xyz.torquato.myapps.api.sound.ISoundRepository
import javax.inject.Inject

class SoundRepository @Inject constructor() : ISoundRepository {

    private external fun touchEvent(action: Int, frequency: Float, amplitude: Float)
    external override fun addTone(frequency: Float, amplitude: Float)
    external fun clean()
    external override fun performControl(control: Boolean)
    private external fun startEngine()
    private external fun stopEngine()

    init {
        System.loadLibrary(LIB_NAME)
    }

    override fun setTouchEvent(action: Int, frequency: Float, amplitude: Float) {
        //touchEvent(action, frequency, amplitude)
        if (action == 0) {
            addTone(frequency, amplitude)
            performControl(true)
        } else
            performControl(false)
    }

    override fun clear() {
        clean()
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