package zed.rainxch.qrcraft.qrcraft.presentation.scan_result

import zed.rainxch.qrcraft.core.domain.model.QRResult

sealed interface ScanResultAction {
    data class OnLoadData(val data: QRResult) : ScanResultAction
    data class OnShare(val data: String) : ScanResultAction
    data class OnCopy(val data: String) : ScanResultAction
}