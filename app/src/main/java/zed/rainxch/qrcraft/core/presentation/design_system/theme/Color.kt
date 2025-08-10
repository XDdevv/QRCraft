package zed.rainxch.qrcraft.core.presentation.design_system.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

val primary = Color(0xffEBFF69)
val surface = Color(0xffEDF2F5)
val surfaceHigher = Color(0xffffffff)
val onSurface = Color(0xff273037)
val onSurfaceAlt = Color(0xff505F6A)
val overlay = Color(0xff000000).copy(alpha = .5f)
val onOverlay = Color(0xffffffff)

val ColorScheme.link get() = Color(0xff373f05)
val ColorScheme.linkBg get() = Color(0xffebff69).copy(alpha = .3f)
val ColorScheme.error get() = Color(0xfff12244)
val ColorScheme.success get() = Color(0xff4dda9d)
val ColorScheme.text get() = Color(0xff583dc5)
val ColorScheme.textBg get() = Color(0xff583dc5).copy(alpha = .1f)
val ColorScheme.contact get() = Color(0xff259570)
val ColorScheme.contactBg get() = Color(0xff259570).copy(alpha = .1f)
val ColorScheme.geo get() = Color(0xffb51d5c)
val ColorScheme.geoBg get() = Color(0xffb51d5c).copy(alpha = .1f)
val ColorScheme.phone get() = Color(0xffC86017)
val ColorScheme.phoneBg get() = Color(0xffC86017).copy(alpha = .1f)
val ColorScheme.wifi get() = Color(0xff1F44CD)
val ColorScheme.wifiBg get() = Color(0xff1F44CD).copy(alpha = .1f)
