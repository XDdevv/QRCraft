package zed.rainxch.qrcraft.core.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import zed.rainxch.qrcraft.qrcraft.data.repository.QRScannerRepositoryImpl
import zed.rainxch.qrcraft.qrcraft.domain.repository.QRScannerRepository
import zed.rainxch.qrcraft.qrcraft.presentation.scan.ScanViewModel

val appModule = module {
    single<QRScannerRepository> { QRScannerRepositoryImpl(get()) }

    viewModel { ScanViewModel(get()) }
}