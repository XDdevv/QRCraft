package zed.rainxch.qrcraft.qrcraft.presentation.scan_result.utils

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder

fun generateQRCode(content: String, size: Int = 512): Bitmap {
    require(content.isNotBlank()) { "QR Code content cannot be empty" }

    val bitMatrix = MultiFormatWriter().encode(
        content,
        BarcodeFormat.QR_CODE,
        size,
        size
    )
    val barcodeEncoder = BarcodeEncoder()
    return barcodeEncoder.createBitmap(bitMatrix)
}
