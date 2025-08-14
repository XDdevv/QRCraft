package zed.rainxch.qrcraft.qrcraft.presentation.scan_result

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import zed.rainxch.qrcraft.qrcraft.domain.repository.ScanResultRepository

class ScanResultViewModel(
    private val scanResultRepository: ScanResultRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ScanResultState())
    val state = _state.asStateFlow()

    fun onAction(action: ScanResultAction) {
        when (action) {
            is ScanResultAction.OnLoadData -> {
                _state.update {
                    it.copy(
                        type = action.data.type!!,
                        data = action.data.value!!
                    )
                }
            }

            is ScanResultAction.OnCopy -> {
                scanResultRepository.copy(action.data)
            }

            is ScanResultAction.OnShare -> {
                scanResultRepository.share(action.data)
            }
        }
    }

}