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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.secam.checkin24.R
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun DayInfo(
    date: LocalDate,
    checkInData: Map<LocalDate, List<LocalTime>>,
    onDelete: (LocalDate, LocalTime) -> Unit,
    modifier: Modifier = Modifier
) {
    val month = date.month.name.lowercase().replaceFirstChar {
        if (it.isLowerCase()) it.uppercaseChar()
        else it
    }
    val dayNum = date.dayOfMonth
    val year = date.year
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        if (checkInData.containsKey(date)) {
            for (time in checkInData[date]!!) {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "$month $dayNum, $year",
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = " at "
                            )
                            Text(
                                text = time.toString(),
                                fontWeight = FontWeight.Bold
                            )
                        }
                        IconButton(
                            onClick = { onDelete(date, time) }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_delete_24px),
                                contentDescription = "delete",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
        else {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = "$month $dayNum, $year",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = " - No check in data"
                    )
                }
            }
        }
    }
}