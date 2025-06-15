package xyz.torquato.myapps.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import xyz.torquato.myapps.ui.components.model.MidiNote

@Composable
fun MusicalKeyboard(
    modifier: Modifier = Modifier,
    onPress: (MidiNote) -> Unit,
    onRelease: () -> Unit
) {
    val semiNoteList = listOf(
        MidiNote.M61_000,
        MidiNote.M63_000,
        MidiNote.EMPTY,
        MidiNote.M66_000,
        MidiNote.M68_000,
        MidiNote.M70_000,
    )

    val noteList = listOf(
        MidiNote.M60_000,
        MidiNote.M62_000,
        MidiNote.M64_000,
        MidiNote.M65_000,
        MidiNote.M67_000,
        MidiNote.M69_000,
        MidiNote.M71_000,
    )
    val baseWidth = 4 * 11
    val baseHeight =  baseWidth * 3 / 2
    val middleHeight = baseHeight / 4
    Box(
        modifier = modifier
            .background(Color(0x11ffffff))
            .border(3.dp, Color.Blue)
            .size((7 * baseWidth).dp, (2 * baseHeight - middleHeight).dp)
    ) {
        semiNoteList.forEachIndexed { index, semiTone ->
            if (semiTone == MidiNote.EMPTY) {
                Box(
                    modifier = Modifier
                        .offset((index * baseWidth).dp, 0.dp)
                        .background(Color.Transparent)
                        .size(baseWidth.dp)
                ) {}
            } else {
                SemiToneButton(
                    modifier = Modifier
                        .offset((index * baseWidth + baseWidth / 2).dp, 0.dp)
                        .height(baseHeight.dp)
                        .width(baseWidth.dp),
                    onPress = onPress,
                    onRelease = onRelease,
                    yFactor = 1f - middleHeight.toFloat() / baseHeight.toFloat(),
                    note = semiTone
                )
            }
        }
        noteList.forEachIndexed { index, note ->
            ToneButton(
                modifier = Modifier
                    .offset((index * baseWidth).dp, (baseHeight - middleHeight).dp)
                    .height(baseHeight.dp)
                    .width(baseWidth.dp),
                onPress = onPress,
                onRelease = onRelease,
                note = note
            )
        }
    }
}