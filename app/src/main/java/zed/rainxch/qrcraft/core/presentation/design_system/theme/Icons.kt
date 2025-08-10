package zed.rainxch.qrcraft.core.presentation.design_system.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import zed.rainxch.qrcraft.R

object QRCraftIcons {
    val Default @Composable get() = DefaultIcons


    object DefaultIcons {
        val alert
            @Composable
            get() =
                ImageVector.vectorResource(R.drawable.ic_alert_triangle)

        val arrow_left
            @Composable
            get() =
                ImageVector.vectorResource(R.drawable.ic_arrow_left)

        val clock_refresh
            @Composable
            get() =
                ImageVector.vectorResource(R.drawable.ic_clock_refresh)

        val copy
            @Composable
            get() =
                ImageVector.vectorResource(R.drawable.ic_copy_01)

        val heart
            @Composable
            get() =
                ImageVector.vectorResource(R.drawable.ic_heart)

        val image
            @Composable
            get() =
                ImageVector.vectorResource(R.drawable.ic_image_01)

        val link
            @Composable
            get() =
                ImageVector.vectorResource(R.drawable.ic_link_01)

        val marked_pin
            @Composable
            get() =
                ImageVector.vectorResource(R.drawable.ic_marker_pin_06)

        val phone
            @Composable
            get() =
                ImageVector.vectorResource(R.drawable.ic_phone)

        val plus_circle
            @Composable
            get() =
                ImageVector.vectorResource(R.drawable.ic_plus_circle)

        val scan
            @Composable
            get() =
                ImageVector.vectorResource(R.drawable.ic_scan)

        val share
            @Composable
            get() =
                ImageVector.vectorResource(R.drawable.ic_share_03)

        val type
            @Composable
            get() =
                ImageVector.vectorResource(R.drawable.ic_type_01)

        val user
            @Composable
            get() =
                ImageVector.vectorResource(R.drawable.ic_user_03)

        val wifi
            @Composable
            get() =
                ImageVector.vectorResource(R.drawable.ic_wifi)

        val zap_off
            @Composable
            get() =
                ImageVector.vectorResource(R.drawable.ic_zap_off)

        val zap_on
            @Composable
            get() =
                ImageVector.vectorResource(R.drawable.ic_zap_on)
    }
}
