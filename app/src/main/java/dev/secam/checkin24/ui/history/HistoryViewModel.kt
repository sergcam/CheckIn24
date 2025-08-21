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
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.secam.checkin24.data.CheckInData
import dev.secam.checkin24.data.HistoryRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

const val TAG = "HistoryViewModel"
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepo: HistoryRepo
) : ViewModel() {
    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState = _uiState.asStateFlow()
    val checkInData = historyRepo.getAllEntriesStream().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = mapOf(),
    )

    fun addCheckIn(date: LocalDate, time: LocalTime) {
        val data = checkInData.value
        if(data.containsKey(date)) {
            if(!data[date]!!.contains(time)){
                val newList = data[date]!!.plus(listOf(time))
                viewModelScope.launch {
                    historyRepo.updateEntry(CheckInData(date,newList.sorted()))
                }
            }
        } else {
            viewModelScope.launch {
                historyRepo.insertEntry(CheckInData(date,listOf(time)))
            }
        }
    }

    fun removeCheckIn(date: LocalDate, time: LocalTime) {
        val data = checkInData.value[date]
        if(data!!.size > 1) {
            val newList = data.minus(listOf(time))
            viewModelScope.launch {
                historyRepo.updateEntry(CheckInData(date,newList))
            }
        } else {
            viewModelScope.launch {
                historyRepo.deleteEntry(date)
            }
        }
    }

    fun clearCheckInData() {
        viewModelScope.launch {
            historyRepo.deleteAll()
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
    val selectedDate: LocalDate = LocalDate.now(),
    val menuExpanded: Boolean = false,
    val showAddCheckInDialog: Boolean = false,
    val showDeleteAllDialog: Boolean = false,
)