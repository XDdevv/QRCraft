package zed.rainxch.qrcraft.qrcraft.presentation.scan_result

import android.graphics.Bitmap
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.androidx.compose.koinViewModel
import zed.rainxch.qrcraft.R
import zed.rainxch.qrcraft.core.domain.model.QRResult
import zed.rainxch.qrcraft.core.domain.model.QRResultType
import zed.rainxch.qrcraft.core.presentation.buttons.QRCraftPrimaryButton
import zed.rainxch.qrcraft.core.presentation.design_system.theme.QRCraftIcons
import zed.rainxch.qrcraft.core.presentation.design_system.theme.QRCraftTheme
import zed.rainxch.qrcraft.core.presentation.texts.QRCraftCollapsableText
import zed.rainxch.qrcraft.core.presentation.texts.QRCraftTextLink
import zed.rainxch.qrcraft.qrcraft.presentation.scan_result.utils.generateQRCode

@Composable
fun ScanResultRoot(
    result: QRResult,
    viewModel: ScanResultViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(result) {
        viewModel.onAction(ScanResultAction.OnLoadData(result))
    }

    ScanResultScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun ScanResultScreen(
    state: ScanResultState,
    onAction: (ScanResultAction) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.onSurface,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.width(380.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                CardContent(
                    state = state,
                    onAction = onAction
                )
            }
        }

    }
}

@Composable
fun CardContent(
    state: ScanResultState,
    onAction: (ScanResultAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .width(380.dp)
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
            .animateContentSize()
            .padding(
                top = 80.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(20.dp))

        val textAlignment = remember(state.type) {
            if (state.type == QRResultType.TEXT) {
                TextAlign.Start
            } else {
                TextAlign.Center
            }
        }

        Text(
            text = state.type.title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.height(10.dp))

        if (state.type == QRResultType.LINK) {
            QRCraftTextLink(
                link = state.data
            )
        } else {
            QRCraftCollapsableText(
                text = state.data,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            QRCraftPrimaryButton(
                text = "Share",
                icon = QRCraftIcons.Default.share,
                onClick = {
                    onAction(ScanResultAction.OnShare(state.data))
                },
                modifier = Modifier.weight(1f)
            )

            QRCraftPrimaryButton(
                text = "Copy",
                icon = QRCraftIcons.Default.copy,
                onClick = {
                    onAction(ScanResultAction.OnCopy(state.data))
                },
                modifier = Modifier.weight(1f)
            )
        }
    }

    val bitmap: Bitmap? = remember(state.data) {
        if (state.data.isNotBlank()) {
            generateQRCode(state.data)
        } else {
            null
        }
    }

    bitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = "QR code",
            modifier = Modifier
                .size(160.dp)
                .offset(y = (-80).dp)
                .clip(RoundedCornerShape(16.dp))
                .border(
                    width = 4.dp,
                    color = MaterialTheme.colorScheme.surfaceContainerHighest,
                    shape = RoundedCornerShape(16.dp)
                )
                .shadow(8.dp, RoundedCornerShape(16.dp))
        )
    }

}

@Preview
@Composable
private fun Preview() {
    QRCraftTheme {
        ScanResultScreen(
            state = ScanResultState(),
            onAction = {

            }
        )
    }
}