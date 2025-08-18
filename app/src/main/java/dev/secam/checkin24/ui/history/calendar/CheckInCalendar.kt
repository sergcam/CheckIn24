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

package dev.secam.checkin24.ui.history.calendar

import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import dev.secam.checkin24.ui.history.HistoryViewModel
import kotlinx.coroutines.launch
import java.time.YearMonth

@Composable
fun CheckInCalendar(viewModel: HistoryViewModel) {
    // ui state
    val uiState = viewModel.uiState.collectAsState().value
    val selectedDate = uiState.selectedDate
    val checkInData = uiState.checkInData

    // calendar
    val scope = rememberCoroutineScope()
    val currentMonth = remember { YearMonth.now()  }
    val startMonth = remember { currentMonth.minusMonths(50) }
    val endMonth = remember { currentMonth }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }
    val daysOfWeek = daysOfWeek(firstDayOfWeek = firstDayOfWeek)
    val calendarState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek,
    )

    ElevatedCard {
        HorizontalCalendar(
            state = calendarState,
            dayContent = {
                if (it.position == DayPosition.MonthDate)
                    Day(
                        it, (selectedDate == it.date),
                        event = checkInData.containsKey(it.date)
                    ) {
                        viewModel.setSelectedDate(it.date)
                    }
                else {
                    InactiveDay(it)
                }
            },
            monthHeader = {
                MonthHeader(
                    it, daysOfWeek,
                    onPrev = {
                        scope.launch {
                            calendarState.animateScrollToMonth(calendarState.firstVisibleMonth.yearMonth.previousMonth)
                        }
                    },
                    onNext = {
                        scope.launch {
                            calendarState.animateScrollToMonth(calendarState.firstVisibleMonth.yearMonth.nextMonth)
                        }
                    }
                )
            }
        )
    }
}