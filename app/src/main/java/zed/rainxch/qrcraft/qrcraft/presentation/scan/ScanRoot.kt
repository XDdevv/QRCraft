package zed.rainxch.qrcraft.qrcraft.presentation.scan

import android.Manifest
import androidx.activity.compose.LocalActivity
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.koin.androidx.compose.koinViewModel
import zed.rainxch.qrcraft.R
import zed.rainxch.qrcraft.core.domain.model.QRResult
import zed.rainxch.qrcraft.core.presentation.dialogs.ActionDialog
import zed.rainxch.qrcraft.core.presentation.dialogs.ActionDialogAction
import zed.rainxch.qrcraft.core.presentation.dialogs.ActionDialogActionType
import zed.rainxch.qrcraft.core.presentation.navigation.NavGraph
import zed.rainxch.qrcraft.core.presentation.utils.ObserveAsEvents
import zed.rainxch.qrcraft.qrcraft.presentation.scan.utils.openSettings

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScanRoot(
    onNavigateToResult: (QRResult) -> Unit,
    viewModel: ScanViewModel = koinViewModel(),
) {
    val activity = LocalActivity.current
    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            ScanEvents.OnCloseAppClick -> {
                activity?.finish()
            }

            ScanEvents.OnOpenPermissionSettings -> {
                openSettings(context)
            }

            is ScanEvents.OnQrDetected -> {
                onNavigateToResult(event.qrResult)
            }
        }
    }

    ScanScreen(
        state = state,
        onAction = viewModel::onAction
    )

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

    LaunchedEffect(Unit) {
        if (!permission.status.isGranted) {
            viewModel.onAction(ScanAction.OnShowPermissionDialog)
        } else {
            viewModel.onAction(ScanAction.OnPermissionsGranted)
        }
    }

    if (state.showPermissionDialog) {
        ActionDialog(
            title = stringResource(R.string.permission_title),
            description = stringResource(R.string.permission_desc),
            onDismissRequest = {
                viewModel.onAction(ScanAction.OnDeclinePermissions)
            },
            actions = listOf(
                ActionDialogAction(
                    title = stringResource(R.string.close_app),
                    action = {
                        viewModel.onAction(ScanAction.OnDeclinePermissions)
                    },
                    type = ActionDialogActionType.NEGATIVE
                ),
                ActionDialogAction(
                    title = stringResource(R.string.grant_access),
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
}


@Composable
fun ScanScreen(
    state: ScanState,
    onAction: (ScanAction) -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView = remember { PreviewView(context) }

    LaunchedEffect(state.hasCameraPermission) {
        if (state.hasCameraPermission) {
            onAction(
                ScanAction.StartScanning(
                    lifecycleOwner = lifecycleOwner,
                    surfaceProvider = previewView.surfaceProvider
                )
            )
        }
    }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            factory = {
                previewView
            },
            modifier = Modifier.fillMaxSize()
        )

        CameraContent()

        Loading(state.isLoading)
    }

}

@Composable
fun Loading(isLoading: Boolean) {
    if (isLoading) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onTertiary,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round
            )

            Text(
                text = stringResource(R.string.loading),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onTertiary
            )
        }
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
                .size(300.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White.copy(alpha = .1f))

        )
    }

}