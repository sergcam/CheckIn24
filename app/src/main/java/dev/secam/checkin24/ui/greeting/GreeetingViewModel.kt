package dev.secam.checkin24.ui.greeting

import android.R
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.secam.checkin24.CheckIn24
import dev.secam.checkin24.data.PreferencesRepo
import dev.secam.checkin24.data.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GreetingViewModel @Inject constructor(
    private val preferencesRepo: PreferencesRepo
) : ViewModel() {
    private val _uiState = MutableStateFlow(GreetingUiState())
    val prefState = preferencesRepo.preferencesFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )
    val uiState = _uiState.asStateFlow()

    fun setQrOpened(qrOpened: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                qrOpened = qrOpened
            )
        }
    }
}

data class GreetingUiState(
    // preferences
    val mbrId: String = "",
    val firstName: String = "",
    val qrOnOpen: Boolean = false,
    val qrMaxBrightness: Boolean = false,
    val useNtp: Boolean = false,
    val ntpServer: String = "",
    val prefsRead: Boolean = false,

    // current session
    val qrOpened: Boolean = false,
    val showBottomSheet: Boolean = false
)