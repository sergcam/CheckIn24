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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.secam.checkin24.R
import dev.secam.checkin24.ui.CheckInScreen
import dev.secam.checkin24.ui.components.CheckInTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GreetingScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: GreetingViewModel = hiltViewModel(),
) {
    // ui state
    val uiState = viewModel.uiState.collectAsState().value
    val qrOpened = uiState.qrOpened
    val showBottomSheet = uiState.showBottomSheet

    // user preferences
    val prefs = viewModel.prefState.collectAsState().value
    val mbrId = prefs.mbrId
    val firstName = prefs.firstName
    val qrOnOpen = prefs.qrOnOpen
    val qrMaxBrightness = prefs.qrMaxBrightness

    if (qrOnOpen && !qrOpened) viewModel.setShowBottomSheet(true)

    Scaffold(
        topBar = {
            CheckInTopBar(
                title = "CheckIn24",
                actionIcon = painterResource(R.drawable.outline_settings_24),
                contentDescription = "settings button"
            ) {
                viewModel.setQrOpened()
                navController.navigate(CheckInScreen.Settings.name)
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Check In") },
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.baseline_qr_code_24),
                        contentDescription = "qr code icon"
                    )
                },
                onClick = {
                    viewModel.setShowBottomSheet(true)
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = modifier
                .padding(contentPadding)
                .padding(horizontal = 16.dp, vertical = 0.dp)
        ) {
            GreetingText(
                firstName = firstName,
                modifier = modifier
                    .padding(top = 54.dp, bottom = 20.dp)
            )
            CheckInTracker()
            GreetingButton(
                text = "Check In History",
                modifier = modifier
                    .padding(top = 16.dp)
            ) { }
            GreetingButton(
                text = "Manage Account",
                icon = painterResource(R.drawable.open_in_new_24px),
                iconDesc = null,
                modifier = modifier
                    .padding(top = 16.dp)
            ) { }
        }
        if (showBottomSheet) {
            viewModel.setQrOpened()
            QrScreen(mbrId, qrMaxBrightness) { viewModel.setShowBottomSheet(false) }
        }
    }
}

@Composable
fun GreetingText(firstName: String, modifier: Modifier = Modifier) {
    Text(
        text = "Let's Go, $firstName",
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Composable
fun GreetingButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = text
        )
    }
}

@Composable
fun GreetingButton(
    text: String,
    icon: Painter,
    iconDesc: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(end = 4.dp)
        )
        Icon(
            painter = icon,
            contentDescription = iconDesc,
            modifier = Modifier
                .size(20.dp)
        )
    }
}