package zed.rainxch.qrcraft.core.presentation.navigation

import kotlinx.serialization.Serializable
import zed.rainxch.qrcraft.core.domain.model.QRResult

sealed interface NavGraph {

    @Serializable
    data object ScanScreen : NavGraph

    @Serializable
    data class ScanResultScreen(
        val qrResult: QRResult
    ): NavGraph
}