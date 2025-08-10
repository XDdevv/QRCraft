package zed.rainxch.qrcraft.core.presentation.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zed.rainxch.qrcraft.core.presentation.design_system.theme.QRCraftIcons
import zed.rainxch.qrcraft.core.presentation.design_system.theme.QRCraftTheme

@Composable
fun QRCraftTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isErrorButton: Boolean = false,
) {
    TextButton(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = if (isErrorButton) {
                MaterialTheme.colorScheme.error
            } else MaterialTheme.colorScheme.onSurface,
            disabledContentColor = MaterialTheme.colorScheme.inverseOnSurface,
        ),
        contentPadding = PaddingValues(
            vertical = 14.dp,
            horizontal = 16.dp
        ),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = QRCraftIcons.Default.scan,
                contentDescription = null
            )

            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                fontSize = 16.sp
            )

            Icon(
                imageVector = QRCraftIcons.Default.scan,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun QRCraftTextButtonPreview() {
    QRCraftTheme {
        QRCraftTextButton(
            text = "Button",
            onClick = {

            }
        )
    }
}

@Preview
@Composable
private fun QRCraftTextButtonErrorPreview() {
    QRCraftTheme {
        QRCraftTextButton(
            text = "Button",
            isErrorButton = true,
            onClick = {

            }
        )
    }
}

@Preview
@Composable
private fun QRCraftTextButtonDisabledPreview() {
    QRCraftTheme {

        QRCraftTextButton(
            text = "Button",
            onClick = {

            },
            enabled = false
        )
    }
}