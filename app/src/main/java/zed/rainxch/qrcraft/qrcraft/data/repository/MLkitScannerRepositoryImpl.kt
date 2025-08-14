package zed.rainxch.qrcraft.qrcraft.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.core.UseCaseGroup
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import zed.rainxch.qrcraft.core.domain.model.QRResult
import zed.rainxch.qrcraft.core.domain.model.QRResultType
import zed.rainxch.qrcraft.qrcraft.domain.repository.QRScannerRepository
import kotlin.math.max
import kotlin.math.min

class MLkitScannerRepositoryImpl(
    private val context: Context,
) : QRScannerRepository {

    override fun startScanning(
        lifecycleOwner: LifecycleOwner,
        surfaceProvider: Preview.SurfaceProvider,
    ): Flow<QRResult?> = callbackFlow {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        val executor = ContextCompat.getMainExecutor(context)

        var cameraProvider: ProcessCameraProvider? = null
        var analysis: ImageAnalysis? = null

        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .apply {
                    setSurfaceProvider(surfaceProvider)
                }

            analysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .apply {
                    setAnalyzer(executor) { imageProxy ->
                        processImageProxy(imageProxy) { qrValue, type ->
                            if (qrValue != null && type != null) {
                                val qrType = getTypeFromQRType(type)
                                trySend(QRResult(qrValue, qrType))
                            } else {
                                trySend(null)
                            }
                        }
                    }
                }

            cameraProvider?.unbindAll()
            cameraProvider?.bindToLifecycle(
                lifecycleOwner = lifecycleOwner,
                cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
                useCaseGroup = UseCaseGroup.Builder()
                    .addUseCase(preview)
                    .addUseCase(analysis)
                    .build()
            )

        }, executor)

        awaitClose {
            analysis?.clearAnalyzer()
            cameraProvider?.unbindAll()
        }
    }

    @OptIn(ExperimentalGetImage::class)
    private fun processImageProxy(
        imageProxy: ImageProxy,
        onResult: (data: String?, type: Int?) -> Unit,
    ) {
        val scanner = BarcodeScanning.getClient(
            BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                .build()
        )

        val mediaImage = imageProxy.image
        if (mediaImage != null) {

            val width = imageProxy.width
            val height = imageProxy.height

            val targetSizePx = minOf(context.dpToPx(300.dp), minOf(width, height))

            val left = max(0, (width - targetSizePx) / 2)
            val top = max(0, (height - targetSizePx) / 2)
            val right = min(width, left + targetSizePx)
            val bottom = min(height, top + targetSizePx)

            val cropRect = Rect(left, top, right, bottom)

            val rotation = imageProxy.imageInfo.rotationDegrees

            val bitmap = imageProxy.toBitmap()
            val croppedBitmap = Bitmap.createBitmap(
                bitmap,
                cropRect.left,
                cropRect.top,
                cropRect.width(),
                cropRect.height()
            )

            val image = InputImage.fromBitmap(croppedBitmap, rotation)

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    val barcode = barcodes.firstOrNull()
                    val value = barcode?.rawValue
                    val type = barcode?.valueType
                    onResult(value, type)
                }
                .addOnFailureListener {
                    onResult(null, null)
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            onResult(null, null)
            imageProxy.close()
        }
    }

    private fun getTypeFromQRType(type: Int): QRResultType {
        return when (type) {
            Barcode.TYPE_TEXT -> QRResultType.TEXT
            Barcode.TYPE_GEO -> QRResultType.GEOLOCATION
            Barcode.TYPE_URL -> QRResultType.LINK
            Barcode.TYPE_CONTACT_INFO -> QRResultType.CONTACT
            Barcode.TYPE_WIFI -> QRResultType.WIFI
            Barcode.TYPE_PHONE -> QRResultType.PHONE_NUMBER
            else -> QRResultType.TEXT
        }
    }

    fun Context.dpToPx(dp: Dp): Int {
        return (dp.value * resources.displayMetrics.density).toInt()
    }

}