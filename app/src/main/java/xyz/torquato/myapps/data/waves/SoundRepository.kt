package xyz.torquato.myapps.data.waves

class SoundRepository(
    libraryName: String = "waves"
) {

    private external fun touchEvent(action: Int, frequency: Float, amplitude: Float)
    private external fun startEngine()
    private external fun stopEngine()

    init {
        System.loadLibrary(libraryName)
    }

    fun setTouchEvent(action: Int, frequency: Float, amplitude: Float) {
        touchEvent(action, frequency, amplitude)
    }

    fun start() {
        startEngine()
    }

    fun destroy() {
        stopEngine()
    }
}