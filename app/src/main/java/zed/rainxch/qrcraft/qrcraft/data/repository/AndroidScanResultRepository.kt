package zed.rainxch.qrcraft.qrcraft.data.repository

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.core.content.getSystemService
import zed.rainxch.qrcraft.qrcraft.domain.repository.ScanResultRepository

class AndroidScanResultRepository (
    private val context: Context
) : ScanResultRepository {
    private val clipboardManager = context.getSystemService<ClipboardManager>()

    override fun copy(value: String) {
        clipboardManager?.setPrimaryClip(ClipData.newPlainText("", value))
    }

    override fun share(value: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, value)
        }
        context.startActivity(Intent.createChooser(intent, "Share via"))
    }
}