package xyz.torquato.myapps.ui.mixer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import xyz.torquato.myapps.api.sound.ISoundRepository
import xyz.torquato.myapps.ui.mixer.model.Note
import xyz.torquato.myapps.ui.mixer.model.Track
import javax.inject.Inject

class MixerViewModel @Inject constructor(
    private val soundRepository: ISoundRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<Note> = MutableStateFlow(Note.empty())

    fun add(frequency: Float, amplitude: Float) {
        soundRepository.setTouchEvent(0, frequency, amplitude)
    }

    fun play(track: Track) {
       // note.tones.forEach { tone ->
       //     soundRepository.setTouchEvent(0, tone.frequency, tone.amplitude)
       // }
        val tone = track.notes.first().tones.first()
        viewModelScope.launch {
            soundRepository.setTouchEvent(0, tone.frequency, tone.amplitude)
        }
    }

    suspend fun play(note: Note) {
        // note.tones.forEach { tone ->
        //     soundRepository.setTouchEvent(0, tone.frequency, tone.amplitude)
        // }
        val tone = note.tones.first()
        //  viewModelScope.launch {
        println("MyTag: Playing $tone")
        soundRepository.setTouchEvent(0, tone.frequency, tone.amplitude)
        delay(note.duration)
        //  }
    }

    fun reset() {
        soundRepository.setTouchEvent(1, 0f, 0f)
    }
}