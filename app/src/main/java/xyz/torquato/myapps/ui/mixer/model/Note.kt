package xyz.torquato.myapps.ui.mixer.model

data class Note(
    val tones: List<Tone>,
    val duration: Long
) {
    companion object {
        fun empty() = Note(
            tones = emptyList(),
            duration = 250L
        )
    }
}