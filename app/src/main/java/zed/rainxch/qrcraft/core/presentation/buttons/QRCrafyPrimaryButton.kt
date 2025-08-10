package zed.rainxch.qrcraft.core.presentation.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import zed.rainxch.qrcraft.core.presentation.design_system.theme.QRCraftIcons
import zed.rainxch.qrcraft.core.presentation.design_system.theme.QRCraftTheme

@Composable
fun QRCraftPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
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
private fun QRCraftPrimaryButtonPreview() {
    QRCraftTheme {
        QRCraftPrimaryButton(
            text = "Button",
            onClick = {

            }
        )
    }
}

@Preview
@Composable
private fun QRCrafyPrimaryButtonDisabledPreview() {
    QRCraftTheme {

        QRCraftPrimaryButton(
            text = "Button",
            onClick = {

            },
            enabled = false
        )
    }
}