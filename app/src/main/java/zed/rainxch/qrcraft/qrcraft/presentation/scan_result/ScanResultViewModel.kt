package zed.rainxch.qrcraft.qrcraft.presentation.scan_result

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ScanResultViewModel : ViewModel() {

    private val _state = MutableStateFlow(ScanResultState())
    val state = _state.asStateFlow()

    fun onAction(action: ScanResultAction) {
        when (action) {
            is ScanResultAction.OnLoadData -> {
                _state.update { it.copy(
                    type = action.data.type,
                    data = action.data.value
                ) }
            }

            is ScanResultAction.OnCopy -> {

            }

            is ScanResultAction.OnShare -> {

            }
        }
    }

}