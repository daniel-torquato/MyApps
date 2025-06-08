package xyz.torquato.myapps.api.sound

interface ISoundRepository {

    fun performControl(control: Boolean)

    fun setTouchEvent(action: Int, frequency: Float, amplitude: Float)

    fun addTone(frequency: Float, amplitude: Float)

    fun start()

    fun clear()

    fun destroy()
}