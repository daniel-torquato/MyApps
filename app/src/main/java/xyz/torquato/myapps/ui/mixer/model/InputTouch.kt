package xyz.torquato.myapps.ui.mixer.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.IntSize

data class InputTouch (
    val size: IntSize,
    val touchList: List<Offset>,
    val scale: IntRange
) {
    companion object {
        val Zero = InputTouch(
            IntSize.Zero,
            emptyList(),
            IntRange.EMPTY
        )
    }
}