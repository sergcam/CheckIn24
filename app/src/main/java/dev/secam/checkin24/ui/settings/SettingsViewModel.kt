package dev.secam.checkin24.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.secam.checkin24.data.AppColorScheme
import dev.secam.checkin24.data.AppTheme
import dev.secam.checkin24.data.PreferencesRepo
import dev.secam.checkin24.data.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesRepo: PreferencesRepo
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val prefState = preferencesRepo.preferencesFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UserPreferences()
    )
    val uiState = _uiState.asStateFlow()
    fun setMbrId(mbrId: String) {
        viewModelScope.launch {
            preferencesRepo.saveMbrId(mbrId)
        }
    }

    fun setFirstName(firstName: String) {
        viewModelScope.launch {
            preferencesRepo.saveFirstName(firstName)
        }
    }

    fun setTheme(theme: AppTheme) {
        viewModelScope.launch {
            preferencesRepo.saveThemePref(theme)
        }
    }

    fun setColorScheme(colorScheme: AppColorScheme) {
        viewModelScope.launch {
            preferencesRepo.saveColorSchemePref(colorScheme)
        }
    }

    fun setQrOnOpen(qrOnOpen: Boolean) {
        viewModelScope.launch {
            preferencesRepo.saveQrOnOpenPref(qrOnOpen)
        }
    }

    fun setQrMaxBrightness(qrMaxBrightness: Boolean) {
        viewModelScope.launch {
            preferencesRepo.saveQrMaxBrightnessPref(qrMaxBrightness)
        }
    }

    fun setPureBlack(pureBlack: Boolean) {
        viewModelScope.launch {
            preferencesRepo.savePureBlackPref(pureBlack)
        }
    }
    fun setUseNtp(useNtp: Boolean) {
        viewModelScope.launch {
            preferencesRepo.saveNtpPref(useNtp)
        }
    }
    fun setNtpServer(ntpServer: String) {
        viewModelScope.launch {
            preferencesRepo.saveNtpServ(ntpServer)
        }
    }
    fun setShowUserInfoDialog(showUserInfoDialog: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                showUserInfoDialog = showUserInfoDialog
            )
        }
    }
    fun setShowThemeDialog(showThemeDialog: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                showThemeDialog = showThemeDialog
            )
        }
    }
    fun setShowColorSchemeDialog(showColorSchemeDialog: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                showColorSchemeDialog = showColorSchemeDialog
            )
        }
    }
    fun setShowNtpDialog(showNtpDialog: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                showNtpDialog = showNtpDialog
            )
        }
    }
}

data class SettingsUiState(
    val showUserInfoDialog: Boolean = false,
    val showThemeDialog: Boolean = false,
    val showColorSchemeDialog: Boolean = false,
    val showNtpDialog: Boolean = false
)