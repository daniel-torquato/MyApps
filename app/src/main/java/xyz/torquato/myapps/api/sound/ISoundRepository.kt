package xyz.torquato.myapps.api.sound

import xyz.torquato.myapps.ui.mixer.model.Tone

interface ISoundRepository {

    fun performControl(control: Boolean)

    fun setTone(frequency: Float, amplitude: Float)

    fun setTones(tones: Array<Tone>)

    fun start()

    fun clear()

    fun destroy()
}