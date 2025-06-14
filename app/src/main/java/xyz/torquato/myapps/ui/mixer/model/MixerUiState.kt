package xyz.torquato.myapps.ui.mixer.model

data class MixerUiState(
    val isRecording: Boolean,
    val recordingIndex: Int,
    val isSaving: Boolean,
    val isTouching: Boolean,
    val isPlaying: Boolean,
    val isEditing: Boolean,
    val currentTone: Tone,
    val track: Track,
    val playingIndex: Int,
    val playingTones: List<Tone>,
    val editingIndex: Pair<Int, Int>
) {

    companion object {
        val Empty: MixerUiState = MixerUiState(
            isRecording = false,
            recordingIndex = -1,
            isSaving = false,
            isPlaying = false,
            isEditing = false,
            track = Track.Empty,
            playingIndex = -1,
            editingIndex = -1 to -1,
            currentTone = Tone(0.0f, 0.0f),
            isTouching = false,
            playingTones = emptyList<Tone>()
        )
    }
}