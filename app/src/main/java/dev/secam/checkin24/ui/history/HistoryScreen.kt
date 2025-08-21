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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.secam.checkin24.R
import dev.secam.checkin24.ui.components.CheckInTopBar
import dev.secam.checkin24.ui.history.calendar.CheckInCalendar
import dev.secam.checkin24.ui.history.calendar.DayInfo
import dev.secam.checkin24.ui.history.dialogs.AddCheckInDialog
import dev.secam.checkin24.ui.history.dialogs.DeleteAllDialog

@Composable
fun HistoryScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = hiltViewModel(),
) {
    // ui state
    val uiState = viewModel.uiState.collectAsState().value
    val selectedDate = uiState.selectedDate
    val expanded = uiState.menuExpanded
    val showAddCheckInDialog = uiState.showAddCheckInDialog
    val showDeleteAllDialog = uiState.showDeleteAllDialog
    val checkInData = viewModel.checkInData.collectAsState().value

    Scaffold(
        topBar = {
            CheckInTopBar(
                title = stringResource(R.string.history_title),
                actionIcon = painterResource(R.drawable.ic_more_vert_24px),
                contentDescription = stringResource(R.string.more),
                onBack = {
                    navController.navigateUp()
                },
                action = {
                    viewModel.setMenuExpanded()
                }
            )
            DropdownMenu(
                expanded = expanded,
                offset = DpOffset(250.dp, (-54).dp),
                onDismissRequest = { viewModel.setMenuExpanded(false) }
            ) {
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.delete_data)) },
                    onClick = {
                        viewModel.setShowDeleteAllDialog()
                        viewModel.setMenuExpanded(false)
                    }
                )
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(stringResource(R.string.add_check_in)) },
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_calendar_add_on_24px),
                        contentDescription = stringResource(R.string.qr_code_icon)
                    )
                },
                onClick = {
                    viewModel.setShowAddCheckInDialog()
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = modifier
                .padding(contentPadding)
                .padding(horizontal = 16.dp)
        ) {
            CheckInCalendar(viewModel)
            DayInfo(
                date = selectedDate,
                checkInData = checkInData,
                onDelete = viewModel::removeCheckIn,
                modifier = modifier
                    .padding(top = 16.dp)
            )
        }
        if (showAddCheckInDialog) {
            AddCheckInDialog(
                onConfirm = viewModel::addCheckIn,
                date = selectedDate,
            ) { viewModel.setShowAddCheckInDialog(false) }
        }
        if(showDeleteAllDialog) {
            DeleteAllDialog(
                onConfirm = { viewModel.clearCheckInData() },
                onCancel = { viewModel.setShowDeleteAllDialog(false) }
            )
        }
    }
}





