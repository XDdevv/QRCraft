package zed.rainxch.qrcraft.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class QRResult(
    val value: String?,
    val type: QRResultType?,
)
