package zed.rainxch.qrcraft.qrcraft.presentation.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import zed.rainxch.qrcraft.core.domain.model.QRResult
import zed.rainxch.qrcraft.core.presentation.snackbar.SnackbarController
import zed.rainxch.qrcraft.core.presentation.snackbar.SnackbarEvent
import zed.rainxch.qrcraft.qrcraft.domain.repository.QRScannerRepository

class ScanViewModel(
    private val qrRepository: QRScannerRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ScanState())
    val state = _state.asStateFlow()

    private val _events = Channel<ScanEvents>()
    val events = _events.receiveAsFlow()

    fun onAction(action: ScanAction) {
        when (action) {
            ScanAction.OnDeclinePermissions -> {
                viewModelScope.launch {
                    _events.send(ScanEvents.OnCloseAppClick)
                }
            }

            ScanAction.OnGrantPermissions -> {
                _state.update {
                    it.copy(
                        showPermissionDialog = false,
                        showSystemPermissionDialog = false
                    )
                }

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

            is ScanAction.StartScanning -> {
                viewModelScope.launch {
                    qrRepository.startScanning(
                        action.lifecycleOwner,
                        action.surfaceProvider
                    )
                        .collect { qrResult ->
                            onAction(
                                ScanAction.OnQrDetected(
                                    qrResult?.value,
                                    qrResult?.type
                                )
                            )
                        }
                }
            }

            is ScanAction.OnQrDetected -> {
                _state.update {
                    it.copy(scannedQr = QRResult(action?.value, action?.type))
                }
            }
        }
    }

}