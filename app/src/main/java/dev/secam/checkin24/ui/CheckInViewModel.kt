/*
 * Copyright (C) 2025  Sergio Camacho
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.secam.checkin24.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
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

class CheckInViewModel(
    private val preferencesRepo: PreferencesRepo
) : ViewModel() {
    private val _uiState = MutableStateFlow(CheckInUiState())
    val uiState = combine(
        _uiState.asStateFlow(),
        preferencesRepo.preferencesFlow
    ) { checkInUiState: CheckInUiState, userPreferences: UserPreferences ->
        CheckInUiState(
            mbrId = userPreferences.mbrId,
            firstName = userPreferences.firstName,
            theme = userPreferences.theme,
            colorScheme = userPreferences.colorScheme,
            qrOnOpen = userPreferences.qrOnOpen,
            qrMaxBrightness = userPreferences.qrMaxBrightness,
            pureBlack = userPreferences.pureBlack,
            qrOpened = checkInUiState.qrOpened,
            prefsRead = true
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CheckInUiState()
    )


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CheckIn24)
                CheckInViewModel(application.preferencesRepo)
            }
        }
    }

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

    fun setTheme(theme: String) {
        viewModelScope.launch {
            preferencesRepo.saveThemePref(theme)
        }
    }

    fun setColorScheme(colorScheme: String) {
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

    fun setQrOpened(qrOpened: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                qrOpened = qrOpened
            )
        }
    }
}

data class CheckInUiState(
    // preferences
    val mbrId: String = "",
    val firstName: String = "",
    val theme: String = "",
    val colorScheme: String = "",
    val qrOnOpen: Boolean = false,
    val qrMaxBrightness: Boolean = false,
    val pureBlack: Boolean = false,
    val prefsRead: Boolean = false,

    // current session
    val qrOpened: Boolean = false
)