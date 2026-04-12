package xyz.torquato.myapps.presentation.viewmodel.music

import androidx.lifecycle.ViewModel
import xyz.torquato.myapps.domain.impl.mixer.SetChannelStateUseCase
import javax.inject.Inject

class MusicController @Inject constructor(
    private val setChannelStateUseCase: SetChannelStateUseCase
): ViewModel() {

    fun enabledChannel(enable: Boolean) {
        setChannelStateUseCase(enable)
    }
}