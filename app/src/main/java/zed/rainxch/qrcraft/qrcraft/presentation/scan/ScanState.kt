package zed.rainxch.qrcraft.qrcraft.presentation.scan

import zed.rainxch.qrcraft.core.domain.model.QRResult

data class ScanState(
    val showPermissionDialog: Boolean = false,
    val showSystemPermissionDialog: Boolean = false,
    val scannedQr: QRResult? = null,
)