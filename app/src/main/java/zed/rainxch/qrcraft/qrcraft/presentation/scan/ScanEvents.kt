package zed.rainxch.qrcraft.qrcraft.presentation.scan

import zed.rainxch.qrcraft.core.domain.model.QRResult

sealed interface ScanEvents {
    data object OnCloseAppClick : ScanEvents
    data object OnOpenPermissionSettings : ScanEvents
    data class OnQrDetected (val qrResult: QRResult) : ScanEvents
}