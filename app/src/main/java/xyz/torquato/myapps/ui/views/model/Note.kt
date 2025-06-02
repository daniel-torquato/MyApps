package xyz.torquato.myapps.ui.views.model

data class Note(
    val start: Long,
    val tones: List<Tone>,
    val duration: Long
) {
    companion object {
        fun empty() = Note(
            start = 0L,
            tones = emptyList(),
            duration = 1000
        )
    }
}