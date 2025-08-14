package zed.rainxch.qrcraft.core.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import zed.rainxch.qrcraft.qrcraft.data.repository.AndroidScanResultRepository
import zed.rainxch.qrcraft.qrcraft.data.repository.MLkitScannerRepositoryImpl
import zed.rainxch.qrcraft.qrcraft.domain.repository.QRScannerRepository
import zed.rainxch.qrcraft.qrcraft.domain.repository.ScanResultRepository
import zed.rainxch.qrcraft.qrcraft.presentation.scan.ScanViewModel
import zed.rainxch.qrcraft.qrcraft.presentation.scan_result.ScanResultViewModel

val appModule = module {
    single<QRScannerRepository> { MLkitScannerRepositoryImpl(get()) }

    viewModel { ScanViewModel(get()) }

    single<ScanResultRepository> { AndroidScanResultRepository(get()) }

    viewModel { ScanResultViewModel(get()) }
}