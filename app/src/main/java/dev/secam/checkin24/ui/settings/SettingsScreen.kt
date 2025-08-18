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

package dev.secam.checkin24.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.secam.checkin24.R
import dev.secam.checkin24.ui.CheckInScreen
import dev.secam.checkin24.ui.components.CheckInTopBar
import dev.secam.checkin24.ui.components.SettingsItem
import dev.secam.checkin24.ui.components.ToggleSettingsItem
import dev.secam.checkin24.ui.settings.dialogs.ColorSchemeDialog
import dev.secam.checkin24.ui.settings.dialogs.NtpDialog
import dev.secam.checkin24.ui.settings.dialogs.ThemeDialog
import dev.secam.checkin24.ui.settings.dialogs.UserInfoDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    //user preferences
    val prefs = viewModel.prefState.collectAsState().value
    val mbrId = prefs.mbrId
    val firstName = prefs.firstName
    val theme = prefs.theme
    val colorScheme = prefs.colorScheme
    val qrOnOpen = prefs.qrOnOpen
    val qrMaxBrightness = prefs.qrMaxBrightness
    val pureBlack = prefs.pureBlack
    val useNtp = prefs.useNtp
    val ntpServer = prefs.ntpServer

    // ui state
    val uiState = viewModel.uiState.collectAsState().value
    val showUserInfoDialog = uiState.showUserInfoDialog
    val showThemeDialog = uiState.showThemeDialog
    val showColorSchemeDialog = uiState.showColorSchemeDialog
    val showNtpDialog = uiState.showNtpDialog

    Scaffold(
        topBar = {
            CheckInTopBar(
                title = "Settings",
            ) { navController.navigateUp() }
        },
        modifier = modifier
    ) { contentPadding ->

        Column(
            modifier = modifier
                .padding(contentPadding)
                .verticalScroll(rememberScrollState())
        ) {
            SettingsItem(
                headlineContent = "Edit User Info",
                supportingContent = mbrId,
                icon = painterResource(R.drawable.person_24px),
            ) {viewModel.setShowUserInfoDialog(true)}

            SettingsItem(
                headlineContent = "Theme",
                supportingContent = theme.displayName,
                icon = viewModel.getThemeIcon(theme)
            ) {viewModel.setShowThemeDialog(true)}

            SettingsItem(
                headlineContent = "Color Scheme",
                supportingContent = colorScheme.displayName,
                icon = painterResource(R.drawable.palette_24px),
                iconColor = MaterialTheme.colorScheme.primary
            ) {viewModel.setShowColorSchemeDialog(true)}

            ToggleSettingsItem(
                headlineContent = "QR Code on Startup",
                supportingContent = "Show QR code on app open",
                currentState = qrOnOpen,
                onToggle = viewModel::setQrOnOpen
            )
            ToggleSettingsItem(
                headlineContent = "Max Brightness QR Code",
                currentState = qrMaxBrightness,
                onToggle = viewModel::setQrMaxBrightness
            )
            ToggleSettingsItem(
                headlineContent = "Pure Black Dark Theme",
                currentState = pureBlack,
                onToggle = viewModel::setPureBlack
            )
            // TODO: implement ntp
            ToggleSettingsItem(
                headlineContent = "Use Internet Time",
                currentState = useNtp,
                enabled = false,
                onToggle = viewModel::setUseNtp,
            )
            SettingsItem(
                headlineContent = "Choose NTP Server",
                supportingContent = ntpServer,
                icon = painterResource(R.drawable.access_time_24px),
                enabled = useNtp
            ) {viewModel.setShowNtpDialog(true)}

            SettingsItem(
                headlineContent = "About",
                icon = painterResource(R.drawable.info_24px)
            ) { navController.navigate(CheckInScreen.About.name) }

            // show dialogs
            if(showUserInfoDialog) {
                UserInfoDialog(
                    mbrId = mbrId,
                    firstName = firstName,
                    viewModel = viewModel
                ) { viewModel.setShowUserInfoDialog(false) }
            }
            if(showThemeDialog) {
                ThemeDialog(
                    theme = theme,
                    viewModel = viewModel
                ) { viewModel.setShowThemeDialog(false) }
            }
            if(showColorSchemeDialog) {
                ColorSchemeDialog(
                    colorScheme = colorScheme,
                    viewModel = viewModel
                ) { viewModel.setShowColorSchemeDialog(false) }
            }
            if(showNtpDialog) {
                NtpDialog(
                    ntpServer = ntpServer,
                    viewModel = viewModel
                ) { viewModel.setShowNtpDialog(false) }
            }
        }
    }
}





