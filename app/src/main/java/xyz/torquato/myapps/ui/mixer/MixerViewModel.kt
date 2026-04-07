package xyz.torquato.myapps.ui.mixer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import xyz.torquato.myapps.ui.mixer.model.Note
import xyz.torquato.myapps.ui.mixer.model.Track
import xyz.torquato.myapps.domain.impl.mixer.CleanUpUseCase
import xyz.torquato.myapps.domain.impl.mixer.PerformControlUseCase
import xyz.torquato.myapps.domain.impl.mixer.SetToneUseCase
import xyz.torquato.myapps.domain.impl.mixer.SetTonesUseCase
import xyz.torquato.myapps.domain.impl.mixer.model.Tone
import javax.inject.Inject
import kotlin.collections.first
import kotlin.collections.isNotEmpty

@HiltViewModel
class MixerViewModel @Inject constructor(
    private val setToneUseCase: SetToneUseCase,
    private val setTonesUseCase: SetTonesUseCase,
    private val performControlUseCase: PerformControlUseCase,
    private val cleanUpUseCase: CleanUpUseCase
) : ViewModel() {

    fun add(frequency: Float, amplitude: Float) {
        setToneUseCase(frequency, amplitude)
    }

    fun play(track: Track) {
        val tone = track.notes.first().tones.first()
        viewModelScope.launch {
            setToneUseCase(tone.frequency, tone.amplitude)
        }
    }

    suspend fun play(note: Note) {
        if (note.tones.isNotEmpty()) {
            println("MyTag: play $note")
            setTonesUseCase(note.tones.toTypedArray())
            performControlUseCase(true)
            delay(note.duration)
            performControlUseCase(false)
        }
    }

    fun play(tones: List<Tone>) {
        if (tones.isNotEmpty()) {
            setTonesUseCase(tones.toTypedArray())
            performControlUseCase(true)
        } else {
            performControlUseCase(false)
        }
    }

    fun reset() {
        performControlUseCase(false)
        cleanUpUseCase()
    }
}