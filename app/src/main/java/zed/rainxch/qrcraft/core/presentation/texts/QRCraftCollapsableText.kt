package zed.rainxch.qrcraft.core.presentation.texts

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult

@Composable
fun QRCraftCollapsableText(
    text: String,
    modifier: Modifier = Modifier,
) {
    var textLayout: TextLayoutResult? = remember { null }
    var expanded by remember { mutableStateOf(false) }
    val maxLines = remember(expanded) { if (expanded) textLayout?.lineCount ?: 10 else 6 }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            onTextLayout = { layout ->
                textLayout = layout
            },
            maxLines = maxLines
        )

        Text(
            text = if (expanded) "Show less" else "Show more",
            style = MaterialTheme.typography.labelLarge,
            color = if (expanded) MaterialTheme.colorScheme.inverseOnSurface else
                MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}