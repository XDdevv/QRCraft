package zed.rainxch.qrcraft.qrcraft.presentation.scan.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun openSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.fromParts("package", context.packageName, null)
    context.startActivity(intent)
}
