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

package dev.secam.checkin24.ui.history.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import dev.secam.checkin24.R
import dev.secam.checkin24.data.AppTheme
import dev.secam.checkin24.ui.theme.CheckIn24Theme
import dev.secam.checkin24.util.SetDialogDim
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter.ofLocalizedDate
import java.time.format.FormatStyle
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCheckInDialog(
    onConfirm: (LocalDate, LocalTime) -> Unit,
    date: LocalDate,
    onDismiss: () -> Unit
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )


    Dialog(onDismissRequest = { onDismiss() }) {
        SetDialogDim()
        Card(
            modifier = Modifier,
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .padding(12.dp)
            ) {
                Column {
                    Text(
                        text = date.format(ofLocalizedDate(FormatStyle.LONG)),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(start = 12.dp, top = 6.dp, bottom = 24.dp)
                            .width(240.dp)
                    )
                    TimePicker(
                        state = timePickerState,
                        colors = TimePickerColors(
                            clockDialColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            selectorColor = MaterialTheme.colorScheme.primary,
                            containerColor = MaterialTheme.colorScheme.error,
                            periodSelectorBorderColor = MaterialTheme.colorScheme.outline,
                            clockDialSelectedContentColor = MaterialTheme.colorScheme.onPrimary,
                            clockDialUnselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            periodSelectorSelectedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            periodSelectorUnselectedContainerColor = Color.Transparent,
                            periodSelectorSelectedContentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                            periodSelectorUnselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            timeSelectorSelectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            timeSelectorUnselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        ),
                        modifier = Modifier
                            .padding(horizontal = 18.dp)
                    )
                }
                Row {
                    TextButton(
                        onClick = { onDismiss() },

                        ) {
                        Text(stringResource(R.string.dialog_cancel))
                    }
                    TextButton(
                        onClick = {
                            onConfirm(date, LocalTime.of(timePickerState.hour,timePickerState.minute))
                            onDismiss()
                        },
                    ) {
                        Text(stringResource(R.string.dialog_save))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AddCheckInPreview(){
    CheckIn24Theme (
        appTheme = AppTheme.Dark
    ){
//        AddCheckInDialog("September 12, 2025") { }
    }
}