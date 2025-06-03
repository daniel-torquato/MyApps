package xyz.torquato.myapps.api.sound

interface ISoundRepository {

    fun setTouchEvent(action: Int, frequency: Float, amplitude: Float)

    fun start()

    fun destroy()
}