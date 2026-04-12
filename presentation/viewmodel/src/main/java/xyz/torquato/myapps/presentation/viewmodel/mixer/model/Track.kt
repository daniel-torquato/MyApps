package xyz.torquato.myapps.presentation.viewmodel.mixer.model

data class Track(
    val start: Long,
    val notes: List<Note>
) {
    companion object {
        val Empty: Track = Track(
            start = 0,
            notes = emptyList()
        )
    }
}
