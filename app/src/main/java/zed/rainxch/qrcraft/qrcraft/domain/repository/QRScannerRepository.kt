package zed.rainxch.qrcraft.qrcraft.domain.repository

import androidx.camera.core.Preview
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.Flow
import zed.rainxch.qrcraft.core.domain.model.QRResult

interface QRScannerRepository {

    // Question : How to avoid passing these lifecycle owner and SurfaceProvider?
    fun startScanning(
        lifecycleOwner: LifecycleOwner,
        surfaceProvider: Preview.SurfaceProvider,
    ): Flow<QRResult?>
}