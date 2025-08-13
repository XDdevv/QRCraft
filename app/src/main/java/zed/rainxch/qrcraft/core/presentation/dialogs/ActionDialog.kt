package zed.rainxch.qrcraft.core.presentation.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import zed.rainxch.qrcraft.core.presentation.buttons.QRCraftPrimaryButton
import zed.rainxch.qrcraft.core.presentation.design_system.theme.QRCraftTheme

data class ActionDialogAction(
    val title: String,
    val action: () -> Unit,
    val type: ActionDialogActionType,
)

enum class ActionDialogActionType {
    POSITIVE,
    NEGATIVE
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionDialog(
    title: String,
    description: String,
    onDismissRequest: () -> Unit,
    actions: List<ActionDialogAction>,
    modifier: Modifier = Modifier,
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        ),
        modifier = modifier
            .width(320.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 24.dp, vertical = 28.dp)
            .semantics {
                contentDescription = "$title. $description"
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                actions.forEach { action ->
                    QRCraftPrimaryButton(
                        text = action.title,
                        onClick = action.action,
                        isErrorButton = action.type == ActionDialogActionType.NEGATIVE,
                        modifier = Modifier
                            .weight(1f)
                            .semantics { contentDescription = action.title }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PermissionDialogPreview() {
    QRCraftTheme {
        ActionDialog(
            "Camera Required",
            "This app cannot function without camera access. To scan QR codes, please grant permission.",
            {},
            listOf(
                ActionDialogAction(
                    title = "Close App",
                    action = {

                    },
                    type = ActionDialogActionType.NEGATIVE
                ),
                ActionDialogAction(
                    title = "Grant Access",
                    action = {

                    },
                    type = ActionDialogActionType.POSITIVE
                )
            )
        )
    }
}