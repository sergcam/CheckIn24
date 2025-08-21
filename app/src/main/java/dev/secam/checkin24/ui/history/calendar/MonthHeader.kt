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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.core.CalendarMonth
import dev.secam.checkin24.R
import java.time.DayOfWeek
import java.time.Year
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun MonthHeader(month: CalendarMonth, daysOfWeek: List<DayOfWeek>, onPrev: () -> Unit, onNext: () -> Unit) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()

    ){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(horizontal = 4.dp)

        ) {
            IconButton(
                onClick = onPrev,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_chevron_left_24px),
                    contentDescription = stringResource(R.string.month_back),
                )
            }
            val headerText = month.yearMonth.month.name.lowercase().replaceFirstChar {
                if (it.isLowerCase()) it.uppercaseChar() else it
            }

            Text(
                text = if(month.yearMonth.year == Year.now().value) headerText
                    else {
                        headerText + " ${month.yearMonth.year}"
                    },
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
            )
            IconButton(
                onClick = onNext,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_chevron_right_24px),
                    contentDescription = stringResource(R.string.month_forward)
                )
            }
        }
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
        ){
            for (day in daysOfWeek) {
                Text(
                    text = day.getDisplayName(TextStyle.NARROW, Locale.getDefault()),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}