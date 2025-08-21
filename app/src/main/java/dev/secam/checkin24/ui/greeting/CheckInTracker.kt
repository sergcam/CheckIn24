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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import dev.secam.checkin24.R
import dev.secam.checkin24.ui.theme.CheckIn24Theme
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CheckInTracker(modifier: Modifier = Modifier, checkIns: List<Boolean>) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        modifier = modifier
            .fillMaxWidth()
            .size(130.dp)

    ) {
        Text(
            text = stringResource(R.string.tracker_widget_header),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier
                .padding(vertical = 10.dp, horizontal = 16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,

            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 10.dp)
        ) {
            val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }
            val daysOfWeek = daysOfWeek(firstDayOfWeek = firstDayOfWeek)
            var i = 0
            for (day in daysOfWeek){
                DayButton(
                    checkedIn = checkIns[i],
                    dayName =  day.getDisplayName(TextStyle.NARROW, Locale.getDefault()),
                    isToday =  day.value == LocalDate.now().dayOfWeek.value,
                )
                i++
            }
        }
    }
}

@Composable
fun DayButton(dayName: String, isToday: Boolean, modifier: Modifier = Modifier, checkedIn: Boolean) {
    val isToggled = checkedIn
    val iconChecked = painterResource(R.drawable.ic_check_24px)
    val iconUnchecked = painterResource(R.drawable.ic_close_24px)
    val buttonContentPadding = PaddingValues(0.dp)
    val buttonPadding = PaddingValues(start = 5.dp, end = 5.dp, bottom = 5.dp)
    val buttonSize = 50.dp
    val buttonColorChecked = ButtonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.primary,
        disabledContentColor = MaterialTheme.colorScheme.onPrimary,
    )
    val buttonColorUnchecked = ButtonColors(
        containerColor = MaterialTheme.colorScheme.inversePrimary,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        disabledContainerColor = MaterialTheme.colorScheme.inversePrimary,
        disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            enabled = false,
            onClick = { },
            contentPadding = buttonContentPadding,
            colors = if (isToggled) buttonColorChecked else buttonColorUnchecked,
            modifier = modifier
                .size(buttonSize)
                .padding(buttonPadding)

        ) {
            Icon(
                painter = if (isToggled) iconChecked else iconUnchecked,
                contentDescription = if (isToggled) stringResource(R.string.check_in_true) else stringResource(R.string.check_in_false)
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .background(
                    color = if (isToday) MaterialTheme.colorScheme.tertiaryContainer else Color.Transparent,
                    shape = CircleShape
                )
                .aspectRatio(1f)
                .size(1.dp)
        ) {
            Text(
                text = dayName,
                color = if (isToday) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.secondary,
            )
        }
    }
}

@Preview
@Composable
fun TrackerPreview(){
    CheckIn24Theme {
//        CheckInTracker()
    }
}