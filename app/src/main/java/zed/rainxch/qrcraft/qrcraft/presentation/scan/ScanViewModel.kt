package zed.rainxch.qrcraft.qrcraft.presentation.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import zed.rainxch.qrcraft.core.presentation.snackbar.SnackbarController
import zed.rainxch.qrcraft.core.presentation.snackbar.SnackbarEvent

class ScanViewModel : ViewModel() {

    private val _state = MutableStateFlow(ScanState())
    val state = _state.asStateFlow()

    private val _events = Channel<ScanEvents>()
    val events = _events.receiveAsFlow()

    fun onAction(action: ScanAction) {
        when (action) {
            is ScanAction.OnQRDetected -> {

            }

            ScanAction.OnDeclinePermissions -> {
                viewModelScope.launch {
                    _events.send(ScanEvents.OnCloseAppClick)
                }
            }

            ScanAction.OnGrantPermissions -> {
                _state.update { it.copy(
                    showPermissionDialog = false,
                    showSystemPermissionDialog = false
                ) }

                viewModelScope.launch {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = "Camera permission granted"
                        )
                    )
                }
            }

            ScanAction.OnRequestSystemPermissionDialog -> {
                _state.update { it.copy(showSystemPermissionDialog = true) }
            }

            ScanAction.OnShowPermissionDialog -> {
                _state.update { it.copy(showPermissionDialog = true) }
            }

            ScanAction.OnOpenPermissionSettings -> {
                viewModelScope.launch {
                    _events.send(ScanEvents.OnOpenPermissionSettings)
                }
            }
        }
    }

}