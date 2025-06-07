package xyz.torquato.myapps.ui.mixer.model

data class MixerUiState(
    val isRecording: Boolean,
    val recordingIndex: Int,
    val isSaving: Boolean,
    val isTouching: Boolean,
    val isPlaying: Boolean,
    val track: Track,
    val playingIndex: Int
) {
    //var size by remember { mutableStateOf(IntSize.Zero) }
    //var points by remember { mutableStateOf(InputTouch.Companion.Zero) }

    //var isRecording by remember { mutableStateOf(false) }
    //var recordingTrack by remember { mutableIntStateOf(-1) }
    //var playingTrack by remember { mutableIntStateOf(-1) }
    //var isSaving by remember { mutableStateOf(false) }
    //var isPlaying by remember { mutableStateOf(false) }

    //var tmpPoints by remember { mutableStateOf(InputTouch.Companion.Zero) }

    //var salvedPoints by remember { mutableStateOf(emptyList<InputTouch>()) }
    //var salvedNotes by remember { mutableStateOf(Track.Empty) }
    //var salvedTimeFactors by remember { mutableStateOf(emptyList<Float>()) }

    //var enabled by remember { mutableStateOf(false) }

    companion object {
        val Empty: MixerUiState = MixerUiState(
            isRecording = false,
            recordingIndex = -1,
            isSaving = false,
            isPlaying = false,
            track = Track.Empty,
            playingIndex = -1,
            isTouching = false
        )
    }
}