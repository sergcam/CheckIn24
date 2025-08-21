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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.secam.checkin24.R
import dev.secam.checkin24.ui.CheckInScreen
import dev.secam.checkin24.ui.components.CheckInTopBar
import dev.secam.checkin24.ui.settings.dialogs.UserInfoDialog
import java.time.LocalDate
import java.time.LocalTime


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
    val showUserInfoDialog = uiState.showUserInfoDialog
    val actionButtonVisible = uiState.actionButtonVisible

    // user preferences
    val prefs = viewModel.prefState.collectAsState().value
    val mbrId = prefs.mbrId
    val firstName = prefs.firstName
    val qrOnOpen = prefs.qrOnOpen
    val qrMaxBrightness = prefs.qrMaxBrightness

    when {
        (mbrId == "") -> viewModel.setActionButtonVisible(false)
        (showBottomSheet) -> viewModel.setActionButtonVisible(false)
        else -> viewModel.setActionButtonVisible(true)
    }


    if (qrOnOpen && !qrOpened) viewModel.setShowBottomSheet(true)
    Scaffold(
        topBar = {
            CheckInTopBar(
                title = stringResource(R.string.app_name),
                actionIcon = painterResource(R.drawable.ic_settings_24px),
                contentDescription = stringResource(R.string.settings_button)
            ) {
                viewModel.setQrOpened()
                navController.navigate(CheckInScreen.Settings.name)
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = actionButtonVisible,
                enter =  scaleIn() + fadeIn(
                    // Fade in with the initial alpha of 0.3f.
                    initialAlpha = 0.3f
                ),
                exit = scaleOut() + fadeOut()
            ) {
                ExtendedFloatingActionButton(
                    text = { Text(stringResource(R.string.check_in_button)) },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_qr_code_24px),
                            contentDescription = stringResource(R.string.qr_code_icon)
                        )
                    },
                    onClick = {
                        viewModel.setShowBottomSheet(true)
                        viewModel.addCheckIn(LocalDate.now(), LocalTime.now())
                    }
                )
            }

        }
    ) { contentPadding ->
        if(mbrId == ""){
            InitialScreen(
                modifier = modifier
                    .padding(contentPadding)
            ) { viewModel.setShowUserInfoDialog(true)}
        } else {
            MainScreen(
                viewModel = viewModel,
                modifier = modifier
                    .padding(contentPadding)
            ) { navController.navigate(CheckInScreen.History.name) }
        }
        if (showBottomSheet) {
            viewModel.setQrOpened()
            QrScreen(mbrId, qrMaxBrightness) {
                viewModel.setShowBottomSheet(false)
            }
        }
        if (showUserInfoDialog) {
            UserInfoDialog(
                mbrId = mbrId,
                firstName = firstName,
                updateId = viewModel::setMbrId,
                updateName = viewModel::setFirstName
            ) {viewModel.setShowUserInfoDialog(false)}
        }
    }
}

@Composable
fun GreetingText(firstName: String, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.greeting) + ", $firstName",
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
            text = text,
            fontWeight = FontWeight.Bold
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
            fontWeight = FontWeight.Bold,
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