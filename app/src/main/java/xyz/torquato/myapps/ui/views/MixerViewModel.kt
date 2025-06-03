package xyz.torquato.myapps.ui.views

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import xyz.torquato.myapps.api.sound.ISoundRepository
import xyz.torquato.myapps.ui.views.model.Note
import javax.inject.Inject

class MixerViewModel @Inject constructor(
    private val soundRepository: ISoundRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<Note> = MutableStateFlow(Note.empty())

    fun add(frequency: Float, amplitude: Float) {
        soundRepository.setTouchEvent(0, frequency, amplitude)
    }

    fun play() {

    }

    fun reset() {
        soundRepository.setTouchEvent(1, 0f, 0f)
    }
}