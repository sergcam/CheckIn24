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

package dev.secam.checkin24.ui

//import androidx.compose.ui.tooling.preview.Preview
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.compose.rememberNavController
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import androidx.navigation.NavHostController
import dev.secam.checkin24.R

//import dev.secam.checkin24.ui.theme.CheckIn24Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    mbrId: String,
    firstName: String,
    theme: String,
    colorScheme: String,
    qrOnOpen: Boolean,
    qrMaxBrightness: Boolean,
    pureBlack: Boolean,
    useNtp: Boolean,
    ntpServer: String,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: CheckInViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                    ),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "back button"
                        )
                    }
                },
                modifier = modifier,
            )
        },
        modifier = modifier
    ) { contentPadding ->

        Column(
            modifier = modifier.padding(contentPadding)
        ) {
            DialogSettingsItem(
                headlineContent = "Edit User Info",
                supportingContent = mbrId,
                which = "UserInfo",
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null
                    )
                },
                mbrId = mbrId,
                firstName = firstName,
                viewModel = viewModel
            )
            DialogSettingsItem(
                headlineContent = "Theme",
                supportingContent =
                    when (theme) {
                        "system" -> "Follow System"
                        "dark" -> "Dark"
                        "light" -> "Light"
                        else -> ""
                    },
                which = "Theme",
                leadingContent = {
                    Icon(
                        painter = when (theme) {
                            "system" -> painterResource(R.drawable.outline_brightness_6_24)
                            "dark" -> painterResource(R.drawable.outline_dark_mode_24)
                            "light" -> painterResource(R.drawable.outline_light_mode_24)
                            else -> painterResource(R.drawable.outline_brightness_6_24)

                        },
                        contentDescription = null
                    )
                },
                theme = theme,
                viewModel = viewModel
            )
            DialogSettingsItem(
                headlineContent = "Color Scheme",
                supportingContent =
                    when (colorScheme) {
                        "dynamic" -> "Dynamic"
                        "red" -> "Red"
                        "orange" -> "Orange"
                        "yellow" -> "Yellow"
                        "green" -> "Green"
                        "blue" -> "Blue"
                        "purple" -> "Purple"
                        else -> ""
                    },
                which = "ColorScheme",
                leadingContent = {
                    Icon(
                        painter = painterResource(R.drawable.outline_palette_24),
                        contentDescription = null
                    )
                },
                colorScheme = colorScheme,
                viewModel = viewModel
            )

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
            DialogSettingsItem(
                headlineContent = "Choose NTP Server",
                supportingContent = ntpServer,
                which = "Ntp",
                leadingContent = {
                    Icon(
                        painter = painterResource(R.drawable.outline_access_time_24),
                        contentDescription = null
                    )
                },
                ntpServer = ntpServer,
                enabled = useNtp,
                viewModel = viewModel
            )

            ListItem(
                headlineContent = { Text("About") },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding()
                    .clickable(enabled = true, onClick = {
                        navController.navigate(CheckInScreen.About.name)
                    })

            )
        }
    }
}

@Composable
fun DialogSettingsItem(
    leadingContent: @Composable (() -> Unit),
    headlineContent: String,
    supportingContent: String,
    which: String,
    modifier: Modifier = Modifier,
    mbrId: String = "",
    firstName: String = "",
    theme: String = "",
    colorScheme: String = "",
    ntpServer: String = "",
    enabled: Boolean = true,
    viewModel: CheckInViewModel
) {
    var showDialog by remember { mutableStateOf(false) }
    ListItem(
        headlineContent = { Text(headlineContent) },
        supportingContent = { Text(supportingContent) },
        leadingContent = leadingContent,
        colors = if (enabled) ListItemDefaults.colors() else ListItemDefaults.colors(
            headlineColor = MaterialTheme.colorScheme.outline,
            supportingColor = MaterialTheme.colorScheme.outlineVariant,
            leadingIconColor = MaterialTheme.colorScheme.outline
        ),
        modifier = modifier
            .clickable(enabled = enabled, onClick = {
                showDialog = true
            })
            .fillMaxWidth()
            .padding()
    )
    if (showDialog) {
        DialogPicker(
            mbrId,
            firstName,
            theme,
            colorScheme,
            ntpServer,
            which,
            viewModel
        ) { showDialog = false }
    }
}

@Composable
fun ToggleSettingsItem(
    modifier: Modifier = Modifier,
    currentState: Boolean,
    headlineContent: String,
    supportingContent: String? = null,
    onToggle: (Boolean) -> Unit
) {
    var checked by remember { mutableStateOf(currentState) }
    ListItem(
        headlineContent = { Text(headlineContent) },
        supportingContent = {
            if (supportingContent != null) {
                Text(supportingContent)
            }
        },
        trailingContent = {
            Switch(
                checked = checked,
                onCheckedChange = null
            )
        },
        modifier = modifier
            .clickable(enabled = true, onClick = {
                checked = !checked
                onToggle(checked)
            })
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 0.dp)
    )
}

@Composable
fun DialogPicker(
    mbrId: String = "",
    firstName: String = "",
    theme: String = "",
    colorScheme: String = "",
    ntpServer: String = "",
    which: String,
    viewModel: CheckInViewModel,
    onDismissRequest: () -> Unit
) {
    when (which) {
        "UserInfo" -> UserInfoDialog(mbrId, firstName, viewModel = viewModel, onDismissRequest)
        "Theme" -> ThemeDialog(theme, viewModel, onDismissRequest)
        "ColorScheme" -> ColorSchemeDialog(colorScheme, viewModel, onDismissRequest)
        "Ntp" -> NtpDialog(ntpServer, viewModel, onDismissRequest)
    }


}


@Composable
fun UserInfoDialog(
    mbrId: String,
    firstName: String = "",
    viewModel: CheckInViewModel,
    onDismissRequest: () -> Unit
) {
    val mbrIdFieldState = rememberTextFieldState(mbrId)
    val nameFieldState = rememberTextFieldState(firstName)
    Dialog(onDismissRequest = { onDismissRequest() }) {
        (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(.25f)
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
                            (viewModel::setMbrId)(mbrIdFieldState.text as String)
                            (viewModel::setFirstName)(nameFieldState.text as String)
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
fun ThemeDialog(theme: String, viewModel: CheckInViewModel, onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(.25f)
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
        ) {
            val radioOptions = listOf("Follow System", "Light", "Dark")
            val curTheme = when (theme) {
                "system" -> 0
                "light" -> 1
                "dark" -> 2
                else -> 0
            }
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[curTheme]) }
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
                radioOptions.forEach { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = {
                                    onOptionSelected(text)
                                    viewModel.setTheme(
                                        when (text) {
                                            "Follow System" -> "system"
                                            "Dark" -> "dark"
                                            "Light" -> "light"
                                            else -> ""
                                        }
                                    )
                                    onDismissRequest()
                                },
                                role = Role.RadioButton
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = null // null recommended for accessibility with screen readers
                        )
                        Text(
                            text = text,
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
    colorScheme: String,
    viewModel: CheckInViewModel,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(.25f)
        Card(
            modifier = Modifier
                .fillMaxWidth(),

            shape = RoundedCornerShape(16.dp),
        ) {
            val radioOptions =
                listOf("Dynamic", "Red", "Orange", "Yellow", "Green", "Blue", "Purple")
            val curColor = when (colorScheme) {
                "dynamic" -> 0
                "red" -> 1
                "orange" -> 2
                "yellow" -> 3
                "green" -> 4
                "blue" -> 5
                "purple" -> 6
                else -> 0
            }
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[curColor]) }

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
                radioOptions.forEach { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = {
                                    onOptionSelected(text)
                                    viewModel.setColorScheme(
                                        when (text) {
                                            radioOptions[0] -> "dynamic"
                                            radioOptions[1] -> "red"
                                            radioOptions[2] -> "orange"
                                            radioOptions[3] -> "yellow"
                                            radioOptions[4] -> "green"
                                            radioOptions[5] -> "blue"
                                            radioOptions[6] -> "purple"
                                            else -> ""
                                        }
                                    )
                                    onDismissRequest()
                                },
                                role = Role.RadioButton
                            ),

                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = null // null recommended for accessibility with screen readers
                        )
                        Text(
                            text = text,
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
    viewModel: CheckInViewModel,
    onDismissRequest: () -> Unit
) {
    val ntpFieldState = rememberTextFieldState(ntpServer)

    Dialog(onDismissRequest = { onDismissRequest() }) {
        (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(.25f)
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
                            (viewModel::setNtpServer)(ntpFieldState.text as String)
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
//@Preview(showBackground = true)
//@Composable
//fun SettingsPreview() {
//    CheckIn24Theme {
//        SettingsScreen(
//            "", "", "System", "Dynamic",
//            qrOnOpen = false,
//            qrMaxBrightness = true,
//            pureBlack = false,
//            navController = rememberNavController(),
//            viewModel = viewModel()
//        )
////        ThemeDialog {  }
////        AboutDialog {  }
//    }
//}