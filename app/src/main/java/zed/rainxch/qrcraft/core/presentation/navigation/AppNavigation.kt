package zed.rainxch.qrcraft.core.presentation.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.coroutines.launch
import kotlinx.serialization.serializer
import zed.rainxch.qrcraft.core.domain.model.QRResult
import zed.rainxch.qrcraft.core.presentation.snackbar.SnackbarController
import zed.rainxch.qrcraft.core.presentation.utils.ObserveAsEvents
import zed.rainxch.qrcraft.qrcraft.presentation.scan.ScanRoot
import zed.rainxch.qrcraft.qrcraft.presentation.scan.components.PermissionSnackbar
import zed.rainxch.qrcraft.qrcraft.presentation.scan_result.ScanResultRoot
import kotlin.reflect.typeOf

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
) {
    val scope = rememberCoroutineScope()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    ObserveAsEvents(
        flow = SnackbarController.events,
        key1 = snackbarHostState
    ) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.title,
                duration = SnackbarDuration.Short
            )

            if (result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                PermissionSnackbar(data)
            }
        }
    ) { _ ->
        NavHost(
            navController = navController,
            startDestination = NavGraph.ScanScreen
        ) {
            composable<NavGraph.ScanScreen> {
                ScanRoot(
                    onNavigateToResult = { qRResult ->
                        navController.navigate(NavGraph.ScanResultScreen(qRResult))
                    }
                )
            }

            composable<NavGraph.ScanResultScreen> (
                typeMap = mapOf(
                    typeOf<QRResult>() to SerializableNavType.create(serializer<QRResult>())
                )
            ) { backStackEntry ->
                val args = backStackEntry.toRoute<NavGraph.ScanResultScreen>()
                ScanResultRoot(
                    result = args.qrResult
                )
            }
        }
    }
}