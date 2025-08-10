package zed.rainxch.qrcraft.core.presentation.design_system.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = primary,
    surface = surface,
    surfaceContainerHighest = surfaceHigher,
    onSurface = onSurface,
    onSurfaceVariant = onSurfaceAlt,
    inverseOnSurface = onSurfaceDisabled,
    tertiary = overlay,
    onTertiary = onOverlay,
    error = error
)

@Composable
fun QRCraftTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}