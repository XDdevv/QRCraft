package zed.rainxch.qrcraft.qrcraft.presentation.scan

sealed interface ScanEvents {
    data object OnCloseAppClick : ScanEvents
    data object OnOpenPermissionSettings : ScanEvents
}