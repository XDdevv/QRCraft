package zed.rainxch.qrcraft.core.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface NavGraph {

    @Serializable
    data object ScanScreen : NavGraph
}