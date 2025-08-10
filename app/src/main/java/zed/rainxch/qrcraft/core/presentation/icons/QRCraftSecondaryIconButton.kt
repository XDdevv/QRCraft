package zed.rainxch.qrcraft.core.presentation.icons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zed.rainxch.qrcraft.core.presentation.design_system.theme.QRCraftIcons
import zed.rainxch.qrcraft.core.presentation.design_system.theme.QRCraftTheme

@Composable
fun QRCraftSecondaryIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    enabled: Boolean = true
) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContentColor = MaterialTheme.colorScheme.inverseOnSurface,
        ),
        modifier = modifier
            .size(44.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}

@Preview
@Composable
private fun QRCraftSecondaryIconButtonPreview() {
    QRCraftTheme {
        QRCraftSecondaryIconButton(
            icon = QRCraftIcons.Default.scan,
            onClick = {}
        )
    }
}
@Preview
@Composable
private fun QRCraftSecondaryIconButtonDisabledPreview() {
    QRCraftTheme {
        QRCraftSecondaryIconButton(
            icon = QRCraftIcons.Default.scan,
            enabled = false,
            onClick = {}
        )
    }
}