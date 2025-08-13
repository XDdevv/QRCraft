package zed.rainxch.qrcraft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import zed.rainxch.qrcraft.core.presentation.design_system.theme.QRCraftTheme
import zed.rainxch.qrcraft.core.presentation.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        enableEdgeToEdge()

        setContent {
            QRCraftTheme {
                AppNavigation()
            }
        }
    }
}