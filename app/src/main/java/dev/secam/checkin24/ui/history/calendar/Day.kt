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

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.CalendarDay
import java.time.LocalDate


@Composable
fun Day(day: CalendarDay, selected: Boolean, event: Boolean, onClick: () -> Unit) {
    val today = day.date == LocalDate.now()
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .aspectRatio(1f)
            .padding(
                all = when {
                    selected && !today -> 8.dp
                    else -> 6.dp
                }
            )

            .clip(CircleShape)
            .background(
                color = when {
                    today -> MaterialTheme.colorScheme.primary
                    selected -> MaterialTheme.colorScheme.surfaceContainerHighest
                    else -> Color.Transparent
                }
            )
            .clickable(
                enabled = true,
                onClick = onClick
            ),

        ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = when {
                today -> MaterialTheme.colorScheme.onPrimary
                selected -> MaterialTheme.colorScheme.onSurfaceVariant
                else -> MaterialTheme.colorScheme.onSurfaceVariant
            }
        )
        val color = if(today) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.tertiary
        if(event){
            Canvas(
                modifier = Modifier
                    .padding(top = 28.dp)
                    .size(4.dp),
                onDraw = {
                    drawCircle(
                        color = color
                    )
                }
            )
        }
    }
}

@Composable
fun InactiveDay(day: CalendarDay) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .aspectRatio(1f)
            .padding(6.dp)
            .clip(CircleShape)
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = MaterialTheme.colorScheme.surfaceContainerHighest
        )
    }
}