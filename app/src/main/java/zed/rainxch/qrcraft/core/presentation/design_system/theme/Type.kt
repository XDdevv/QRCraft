package zed.rainxch.qrcraft.core.presentation.design_system.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import zed.rainxch.qrcraft.R

private val suse_font = FontFamily(
    Font(R.font.suse_regular, FontWeight.Normal),
    Font(R.font.suse_medium, FontWeight.Medium),
    Font(R.font.suse_semi_bold, FontWeight.SemiBold),
)

val Typography = Typography(
    titleMedium = TextStyle(
        fontFamily = suse_font,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 1.1.em
    ),
    titleSmall = TextStyle(
        fontFamily = suse_font,
        fontWeight = FontWeight.SemiBold,
        fontSize = 19.sp,
        lineHeight = 1.1.em
    ),
    labelLarge = TextStyle(
        fontFamily = suse_font,
        fontWeight = FontWeight.Medium,
        lineHeight = 1.1.em,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = suse_font,
        fontWeight = FontWeight.Normal,
        lineHeight = 1.1.em,
        fontSize = 20.sp
    ),
)