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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.secam.checkin24.R


@Composable
fun MainScreen(viewModel: GreetingViewModel,  modifier: Modifier = Modifier, onHistory: () -> Unit) {
    val uriHandler = LocalUriHandler.current

    // user preferences
    val prefs = viewModel.prefState.collectAsState().value
    val firstName = prefs.firstName

    // check in history
    val checkInData = viewModel.checkInData.collectAsState().value

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 0.dp)
    ) {
        GreetingText(
            firstName = firstName,
            modifier = Modifier
                .padding(top = 54.dp, bottom = 20.dp)
        )
        CheckInTracker(
            checkIns = viewModel.getWeekCheckIns(checkInData)
        )
        GreetingButton(
            text = stringResource(R.string.check_in_history),
            modifier = Modifier
                .padding(top = 16.dp)
        ) {
            onHistory()
        }
        GreetingButton(
            text = stringResource(R.string.manage_account),
            icon = painterResource(R.drawable.ic_open_in_new_24px),
            iconDesc = null,
            modifier = Modifier
                .padding(top = 16.dp)
        ) { uriHandler.openUri("https://www.24hourfitness.com/login.html") }
    }
}