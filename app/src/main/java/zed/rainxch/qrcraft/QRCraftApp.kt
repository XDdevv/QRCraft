package zed.rainxch.qrcraft

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import zed.rainxch.qrcraft.core.di.appModule

class QRCraftApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val modules = listOf(
            appModule
        )

        startKoin {
            androidLogger(level = Level.ERROR)
            androidContext(this@QRCraftApp)
            modules(modules)
        }
    }
}