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

package dev.secam.checkin24.ui.greeting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.secam.checkin24.data.CheckInData
import dev.secam.checkin24.data.HistoryRepo
import dev.secam.checkin24.data.PreferencesRepo
import dev.secam.checkin24.data.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

const val TAG = "GreetingViewModel"
@HiltViewModel
class GreetingViewModel @Inject constructor(
    private val preferencesRepo: PreferencesRepo,
    private val historyRepo: HistoryRepo
) : ViewModel() {
    private val _uiState = MutableStateFlow(GreetingUiState())
    val prefState = preferencesRepo.preferencesFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UserPreferences()
    )
    val checkInData = historyRepo.getAllEntriesStream().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = mapOf(),
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
    fun setShowUserInfoDialog(showUserInfoDialog: Boolean){
        _uiState.update { currentState ->
            currentState.copy(
                showUserInfoDialog = showUserInfoDialog
            )
        }
    }

    fun setActionButtonVisible(actionButtonVisible: Boolean){
        _uiState.update { currentState ->
            currentState.copy(
                actionButtonVisible = actionButtonVisible
            )
        }
    }

    fun addCheckIn(date: LocalDate, time: LocalTime) {
        val data = checkInData.value
        val time = time.truncatedTo(ChronoUnit.MINUTES)
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

    fun getWeekCheckIns(checkInData: Map<LocalDate, List<LocalTime>>): List<Boolean>{
        val firstDayOfWeek = firstDayOfWeekFromLocale()
        val daysOfWeek = daysOfWeek(firstDayOfWeek = firstDayOfWeek)
        val today = LocalDate.now()
        val todayPos = daysOfWeek.indexOf(today.dayOfWeek)
        val checkIns = mutableListOf(false,false,false,false,false,false,false)
        for(i in 0..todayPos){
            checkIns[i] = checkInData.containsKey(today.minusDays((todayPos-i).toLong()))
        }
        return checkIns as List<Boolean>
    }
}

data class GreetingUiState(
    val qrOpened: Boolean = false,
    val showBottomSheet: Boolean = false,
    val showUserInfoDialog: Boolean = false,
    val actionButtonVisible: Boolean = false
)