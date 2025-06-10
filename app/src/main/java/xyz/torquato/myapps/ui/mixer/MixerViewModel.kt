package xyz.torquato.myapps.ui.mixer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import xyz.torquato.myapps.api.sound.ISoundRepository
import xyz.torquato.myapps.ui.mixer.model.Note
import xyz.torquato.myapps.ui.mixer.model.Tone
import xyz.torquato.myapps.ui.mixer.model.Track
import javax.inject.Inject

class MixerViewModel @Inject constructor(
    private val soundRepository: ISoundRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<Note> = MutableStateFlow(Note.empty())

    fun add(frequency: Float, amplitude: Float) {
        soundRepository.setTone(frequency, amplitude)
    }

    fun play(track: Track) {
        val tone = track.notes.first().tones.first()
        viewModelScope.launch {
            soundRepository.setTone(tone.frequency, tone.amplitude)
        }
    }

    suspend fun play(note: Note) {
        if (note.tones.isNotEmpty()) {
            println("MyTag: play $note")
            soundRepository.setTones(note.tones.toTypedArray())
            soundRepository.performControl(true)
            delay(note.duration)
            soundRepository.performControl(false)
        }
    }

    fun play(tones: List<Tone>) {
        if (tones.isNotEmpty()) {
            soundRepository.setTones(tones.toTypedArray())
            soundRepository.performControl(true)
        } else {
            soundRepository.performControl(false)
        }
    }

    fun reset() {
        soundRepository.performControl(false)
        soundRepository.clear()
    }
}