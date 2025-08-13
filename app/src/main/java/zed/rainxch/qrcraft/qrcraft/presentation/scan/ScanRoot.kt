package zed.rainxch.qrcraft.qrcraft.presentation.scan

import android.Manifest
import androidx.activity.compose.LocalActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import zed.rainxch.qrcraft.R
import zed.rainxch.qrcraft.core.presentation.design_system.theme.QRCraftTheme
import zed.rainxch.qrcraft.core.presentation.dialogs.ActionDialog
import zed.rainxch.qrcraft.core.presentation.dialogs.ActionDialogAction
import zed.rainxch.qrcraft.core.presentation.dialogs.ActionDialogActionType
import zed.rainxch.qrcraft.core.presentation.utils.ObserveAsEvents
import zed.rainxch.qrcraft.qrcraft.presentation.scan.utils.openSettings

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScanRoot(
    viewModel: ScanViewModel = viewModel(),
) {
    val activity = LocalActivity.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val permission =
        rememberPermissionState(
            permission = Manifest.permission.CAMERA,
            onPermissionResult = { isGranted ->
                if (isGranted) {
                    viewModel.onAction(ScanAction.OnGrantPermissions)
                } else {
                    viewModel.onAction(ScanAction.OnDeclinePermissions)
                }
            }
        )

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            ScanEvents.OnCloseAppClick -> {
                activity?.finish()
            }

            ScanEvents.OnOpenPermissionSettings -> {
                openSettings(context)
            }
        }
    }

    LaunchedEffect(Unit) {
        if (!permission.status.isGranted) {
            viewModel.onAction(ScanAction.OnShowPermissionDialog)
        }
    }

    if (state.showPermissionDialog) {
        ActionDialog(
            title = "Camera Required",
            description = "This app cannot function without camera access. To scan QR codes, please grant permission.",
            onDismissRequest = {
                viewModel.onAction(ScanAction.OnDeclinePermissions)
            },
            actions = listOf(
                ActionDialogAction(
                    title = "Close App",
                    action = {
                        viewModel.onAction(ScanAction.OnDeclinePermissions)
                    },
                    type = ActionDialogActionType.NEGATIVE
                ),
                ActionDialogAction(
                    title = "Grant Access",
                    action = {
                        viewModel.onAction(ScanAction.OnRequestSystemPermissionDialog)
                    },
                    type = ActionDialogActionType.POSITIVE
                )
            )
        )
    }

    if (state.showSystemPermissionDialog) {
        LaunchedEffect(Unit) {
            permission.launchPermissionRequest()
        }
    }

    ScanScreen(
        state = state,
        onAction = viewModel::onAction
    )
}


@Composable
fun ScanScreen(
    state: ScanState,
    onAction: (ScanAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        QrScanner { result, type ->
            if (result != null && type != null) {
                onAction(ScanAction.OnQRDetected(result, type))
            }
        }

        CameraContent()
    }

}

@Composable
fun CameraContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = .5f))
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.point_your_camera_at_a_qr_code),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.titleSmall
        )

        Spacer(Modifier.height(30.dp))

        Image(
            painter = painterResource(R.drawable.ic_camera_frame),
            contentDescription = null,
            modifier = Modifier
                .size(308.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White.copy(alpha = .1f))

        )
    }

}

@Composable
fun QrScanner(
    onQrResult: (qrValue: String?, type: Int?) -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    var lastScannedValue by remember { mutableStateOf<String?>(null) }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            PreviewView(ctx).apply {
                scaleType = PreviewView.ScaleType.FILL_CENTER

                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()

                    val preview = androidx.camera.core.Preview.Builder().build().also {
                        it.surfaceProvider = surfaceProvider
                    }

                    val analysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also { imageAnalysis ->
                            imageAnalysis.setAnalyzer(
                                ContextCompat.getMainExecutor(ctx)
                            ) { imageProxy ->
                                processImageProxy(imageProxy) { qrValue, type ->
                                    if (qrValue != lastScannedValue) {
                                        lastScannedValue = qrValue
                                        onQrResult(qrValue, type)
                                    }
                                }
                            }
                        }

                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        preview,
                        analysis
                    )
                }, ContextCompat.getMainExecutor(ctx))
            }
        }
    )
}

@androidx.annotation.OptIn(ExperimentalGetImage::class)
private fun processImageProxy(
    imageProxy: ImageProxy,
    onResult: (String?, Int?) -> Unit,
) {
    val scanner = BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
    )
    val mediaImage = imageProxy.image
    if (mediaImage != null) {
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
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

@Preview
@Composable
private fun Preview() {
    QRCraftTheme {
        ScanScreen(
            state = ScanState(),
            onAction = {}
        )
    }
}