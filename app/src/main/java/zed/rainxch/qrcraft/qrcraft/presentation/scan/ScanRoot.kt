package zed.rainxch.qrcraft.qrcraft.presentation.scan

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import zed.rainxch.qrcraft.core.presentation.design_system.theme.QRCraftTheme
import zed.rainxch.qrcraft.core.presentation.texts.QRCraftTextLink

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScanRoot(
    viewModel: ScanViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val permissions = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )
    )

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