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

package dev.secam.checkin24.ui.history

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    //preferencesRepo: PreferencesRepo
) : ViewModel() {
    private val _uiState = MutableStateFlow(HistoryUiState())
//    val prefState = preferencesRepo.preferencesFlow.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5_000),
//        initialValue = UserPreferences()
//    )
    val uiState = _uiState.asStateFlow()

    fun addCheckIn(date: LocalDate, time: LocalTime) {
        val newList =
            if(uiState.value.checkInData.containsKey(date)) uiState.value.checkInData[date]!!.plus(listOf(time))
            else listOf(time)
        _uiState.update { currentState ->
            currentState.copy(
                checkInData = uiState.value.checkInData + Pair(date, newList.sorted())
            )
        }
    }

    fun removeCheckIn(date: LocalDate, time: LocalTime) {
        if (uiState.value.checkInData.containsKey(date)){
            val newMap =
                if (uiState.value.checkInData[date]?.size == 1) uiState.value.checkInData - date
                else uiState.value.checkInData + Pair(
                    date,
                    uiState.value.checkInData[date]!! - time
                )
            _uiState.update { currentState ->
                currentState.copy(
                    checkInData = newMap
                )
            }
        }
    }

    fun clearCheckInData() {
        _uiState.update { currentState ->
            currentState.copy(
                checkInData = mapOf()
            )
        }
    }

    fun setSelectedDate(date: LocalDate){
        _uiState.update { currentState ->
            currentState.copy(
                selectedDate = date
            )
        }
    }

    fun setMenuExpanded(menuExpanded: Boolean = true) {
        _uiState.update { currentState ->
            currentState.copy(
                menuExpanded = menuExpanded
            )
        }
    }
    fun setShowAddCheckInDialog(showAddCheckInDialog: Boolean = true) {
        _uiState.update { currentState ->
            currentState.copy(
                showAddCheckInDialog = showAddCheckInDialog
            )
        }
    }

    fun setShowDeleteAllDialog(showDeleteAllDialog: Boolean = true) {
        _uiState.update { currentState ->
            currentState.copy(
                showDeleteAllDialog = showDeleteAllDialog
            )
        }
    }
}

data class HistoryUiState(
    val checkInData: Map<LocalDate, List<LocalTime>> = mapOf(),
    val selectedDate: LocalDate = LocalDate.now(),
    val menuExpanded: Boolean = false,
    val showAddCheckInDialog: Boolean = false,
    val showDeleteAllDialog: Boolean = false,
)