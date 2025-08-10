package zed.rainxch.qrcraft.qrcraft.presentation.scan

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import zed.rainxch.qrcraft.core.presentation.design_system.theme.QRCraftTheme

@Composable
fun ScanRoot(
    viewModel: ScanViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

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