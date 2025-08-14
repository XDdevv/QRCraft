package zed.rainxch.qrcraft.qrcraft.domain.repository

interface ScanResultRepository {
    fun copy(value: String)
    fun share(value: String)
}