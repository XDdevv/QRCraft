package zed.rainxch.qrcraft.core.presentation.texts

import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import zed.rainxch.qrcraft.core.presentation.design_system.theme.QRCraftTheme
import zed.rainxch.qrcraft.core.presentation.design_system.theme.link
import zed.rainxch.qrcraft.core.presentation.design_system.theme.linkBg

@Composable
fun QRCraftTextLink(
    link: String,
    modifier: Modifier = Modifier,
) {
    val activity = LocalActivity.current
    val intent = remember {
        Intent(Intent.ACTION_VIEW).apply {
            setData(link.toUri())
        }
    }

    Text(
        text = link,
        style = MaterialTheme.typography.labelLarge,
        fontSize = 16.sp,
        modifier = modifier
            .background(MaterialTheme.colorScheme.linkBg)
            .clickable(
                onClick = {
                    activity?.startActivity(intent)
                }
            ),
        color = MaterialTheme.colorScheme.link,
    )
}

@Preview
@Composable
private fun QRCraftTextLinkPreview() {
    QRCraftTheme {
        QRCraftTextLink(
            link = "https://www.google.com/maps"
        )
    }
}