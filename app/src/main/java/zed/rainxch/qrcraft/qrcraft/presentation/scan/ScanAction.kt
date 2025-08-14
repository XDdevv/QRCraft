package zed.rainxch.qrcraft.qrcraft.presentation.scan

import androidx.camera.core.Preview
import androidx.lifecycle.LifecycleOwner
import zed.rainxch.qrcraft.core.domain.model.QRResultType

sealed interface ScanAction {
    data object OnDeclinePermissions : ScanAction
    data object OnGrantPermissions : ScanAction
    data object OnShowPermissionDialog : ScanAction
    data object OnRequestSystemPermissionDialog : ScanAction
    data object OnOpenPermissionSettings : ScanAction
    data class StartScanning(
        val lifecycleOwner: LifecycleOwner,
        val surfaceProvider: Preview.SurfaceProvider,
    ) : ScanAction

    data class OnQrDetected(val value: String?, val type: QRResultType?) : ScanAction
}