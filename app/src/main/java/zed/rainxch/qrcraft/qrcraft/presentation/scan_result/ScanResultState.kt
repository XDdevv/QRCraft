package zed.rainxch.qrcraft.qrcraft.presentation.scan_result

import zed.rainxch.qrcraft.core.domain.model.QRResultType

data class ScanResultState(
    val type: QRResultType = QRResultType.TEXT,
    val data: String = ""
)