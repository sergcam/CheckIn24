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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.secam.checkin24.R
import dev.secam.checkin24.data.AppColorScheme
import dev.secam.checkin24.data.AppTheme
import dev.secam.checkin24.ui.CheckInScreen
import dev.secam.checkin24.ui.components.CheckInTopBar

const val dimAmount = .25f
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
            modifier = modifier.padding(contentPadding)
        ) {
            SettingsItem(
                headlineContent = "Edit User Info",
                supportingContent = mbrId,
                icon = painterResource(R.drawable.person_24px),
            ) {viewModel.setShowUserInfoDialog(true)}

            SettingsItem(
                headlineContent = "Theme",
                supportingContent = theme.displayName,
                icon = when (theme) {
                    AppTheme.System -> painterResource(R.drawable.outline_brightness_6_24)
                    AppTheme.Dark -> painterResource(R.drawable.outline_dark_mode_24)
                    AppTheme.Light -> painterResource(R.drawable.outline_light_mode_24)
                }
            ) {viewModel.setShowThemeDialog(true)}

            SettingsItem(
                headlineContent = "Color Scheme",
                supportingContent = colorScheme.displayName,
                icon = painterResource(R.drawable.outline_palette_24)
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
            ToggleSettingsItem(
                headlineContent = "Use Internet Time",
                currentState = useNtp,
                onToggle = viewModel::setUseNtp
            )
            SettingsItem(
                headlineContent = "Choose NTP Server",
                supportingContent = ntpServer,
                icon = painterResource(R.drawable.outline_access_time_24),
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

@Composable
fun SettingsItem(
    headlineContent: String,
    icon: Painter,
    modifier: Modifier = Modifier,
    supportingContent: String = "",
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = { Text(headlineContent,fontWeight = FontWeight.Medium) },
        supportingContent = { Text(supportingContent) },
        leadingContent = {
            Icon(
                painter = icon,
                contentDescription = null
            )
        },
        colors = if (enabled) ListItemDefaults.colors() else ListItemDefaults.colors(
            headlineColor = MaterialTheme.colorScheme.outline,
            supportingColor = MaterialTheme.colorScheme.outlineVariant,
            leadingIconColor = MaterialTheme.colorScheme.outline
        ),
        modifier = modifier
            .clickable(enabled = enabled, onClick = {
                onClick()
            })
            .fillMaxWidth()
            .padding()
    )
}

@Composable
fun ToggleSettingsItem(
    modifier: Modifier = Modifier,
    currentState: Boolean,
    headlineContent: String,
    supportingContent: String? = null,
    onToggle: (Boolean) -> Unit
) {
    ListItem(
        headlineContent = { Text(headlineContent,fontWeight = FontWeight.Medium) },
        supportingContent = {
            if (supportingContent != null) {
                Text(supportingContent)
            }
        },
        trailingContent = {
            Switch(
                checked = currentState,
                onCheckedChange = null
            )
        },
        modifier = modifier
            .clickable(enabled = true, onClick = {
                onToggle(!currentState)
            })
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 0.dp)
    )
}

//@Composable
//fun DialogPicker(
//    mbrId: String = "",
//    firstName: String = "",
//    theme: String = "",
//    colorScheme: String = "",
//    ntpServer: String = "",
//    which: String,
//    viewModel: CheckInViewModel,
//    onDismissRequest: () -> Unit
//) {
//    when (which) {
//        "UserInfo" -> UserInfoDialog(mbrId, firstName, viewModel = viewModel, onDismissRequest)
//        "Theme" -> ThemeDialog(theme, viewModel, onDismissRequest)
//        "ColorScheme" -> ColorSchemeDialog(colorScheme, viewModel, onDismissRequest)
//        "Ntp" -> NtpDialog(ntpServer, viewModel, onDismissRequest)
//    }
//
//
//}


@Composable
fun UserInfoDialog(
    mbrId: String,
    firstName: String = "",
    viewModel: SettingsViewModel,
    onDismissRequest: () -> Unit
) {
    val mbrIdFieldState = rememberTextFieldState(mbrId)
    val nameFieldState = rememberTextFieldState(firstName)
    Dialog(onDismissRequest = { onDismissRequest() }) {
        SetDim(dimAmount)
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(horizontal = 26.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(top = 20.dp)
                ) {
                    Text(
                        text = "Edit User Info",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 26.dp),

                        )
                    OutlinedTextField(
                        state = mbrIdFieldState,
                        placeholder = { Text("MBR########") },
                        label = { Text("Member Number") },
                        modifier = Modifier.padding(bottom = 6.dp),
                    )
                    OutlinedTextField(
                        state = nameFieldState,
                        label = { Text("First Name (Optional)") }
                    )

                }
                Row(
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 14.dp)
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },

                        ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = {
                            viewModel.setMbrId(mbrIdFieldState.text as String)
                            viewModel.setFirstName(nameFieldState.text as String)
                            onDismissRequest()
                        },
                    ) {
                        Text("Save")
                    }
                }
            }
        }

    }
}

@Composable
fun ThemeDialog(
    theme: AppTheme,
    viewModel: SettingsViewModel,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        SetDim(dimAmount)
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
        ) {
            val radioOptions = AppTheme.entries.toList()
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(theme ) }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(horizontal = 26.dp, vertical = 20.dp)
                    .selectableGroup()
            ) {
                Text(
                    text = "Theme",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp),
                )
                radioOptions.forEach { option ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .selectable(
                                selected = (option == selectedOption),
                                onClick = {
                                    onOptionSelected(option)
                                    viewModel.setTheme(option)
                                    onDismissRequest()
                                },
                                role = Role.RadioButton
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (option == selectedOption),
                            onClick = null // null recommended for accessibility with screen readers
                        )
                        Text(
                            text = option.displayName,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ColorSchemeDialog(
    colorScheme: AppColorScheme,
    viewModel: SettingsViewModel,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        SetDim(dimAmount)
        Card(
            modifier = Modifier
                .fillMaxWidth(),

            shape = RoundedCornerShape(16.dp),
        ) {
            val radioOptions = AppColorScheme.entries.toList()
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(colorScheme) }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(horizontal = 26.dp, vertical = 20.dp)
                    .selectableGroup()
            ) {
                Text(
                    text = "Color Scheme",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp),
                )
                radioOptions.forEach { option ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .selectable(
                                selected = (option == selectedOption),
                                onClick = {
                                    onOptionSelected(option)
                                    viewModel.setColorScheme(option)
                                    onDismissRequest()
                                },
                                role = Role.RadioButton
                            ),

                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (option == selectedOption),
                            onClick = null // null recommended for accessibility with screen readers
                        )
                        Text(
                            text = option.displayName,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NtpDialog(
    ntpServer: String,
    viewModel: SettingsViewModel,
    onDismissRequest: () -> Unit
) {
    val ntpFieldState = rememberTextFieldState(ntpServer)

    Dialog(onDismissRequest = { onDismissRequest() }) {
        SetDim(dimAmount)
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(horizontal = 26.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(top = 20.dp)
                ) {
                    Text(
                        text = "Edit NTP Server",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 26.dp),

                        )
                    OutlinedTextField(
                        state = ntpFieldState,
                        label = { Text("NTP Server") },
                        modifier = Modifier.padding(bottom = 6.dp),
                    )

                }
                Row(
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 14.dp)
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },

                        ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = {
                            viewModel.setNtpServer(ntpFieldState.text as String)
                            onDismissRequest()
                        },
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

@Composable
fun SetDim(amount: Float){
    (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(amount)
}