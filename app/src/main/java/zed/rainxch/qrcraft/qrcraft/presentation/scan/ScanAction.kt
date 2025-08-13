package zed.rainxch.qrcraft.qrcraft.presentation.scan

sealed interface ScanAction {
    data class OnQRDetected(val data: String, val type: Int) : ScanAction
    data object OnDeclinePermissions : ScanAction
    data object OnGrantPermissions : ScanAction
    data object OnShowPermissionDialog : ScanAction
    data object OnRequestSystemPermissionDialog : ScanAction
    data object OnOpenPermissionSettings : ScanAction
}