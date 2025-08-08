package dev.secam.checkin24.ui.greeting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.secam.checkin24.data.PreferencesRepo
import dev.secam.checkin24.data.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GreetingViewModel @Inject constructor(
    private val preferencesRepo: PreferencesRepo
) : ViewModel() {
    private val _uiState = MutableStateFlow(GreetingUiState())
    val prefState = preferencesRepo.preferencesFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UserPreferences()
    )
    val uiState = _uiState.asStateFlow()

    fun setQrOpened() {
        _uiState.update { currentState ->
            currentState.copy(
                qrOpened = true
            )
        }
    }
    fun setShowBottomSheet(showBottomSheet: Boolean){
        _uiState.update { currentState ->
            currentState.copy(
                showBottomSheet = showBottomSheet
            )
        }
    }
}

data class GreetingUiState(
    val qrOpened: Boolean = false,
    val showBottomSheet: Boolean = false
)