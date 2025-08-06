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

import android.accessibilityservice.GestureDescription
import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.secam.checkin24.R
import dev.secam.checkin24.ui.CheckInScreen
import dev.secam.checkin24.ui.components.CheckInTopBar
import dev.secam.checkin24.ui.theme.CheckIn24Theme

@OptIn(ExperimentalMaterial3Api::class )
@Composable
fun GreetingScreen(
    mbrId: String,
    firstName: String,
    qrOnOpen: Boolean,
    qrOpened: Boolean,
    qrMaxBrightness: Boolean,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    setQrOpened: () -> Unit
) {

    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    if(qrOnOpen && !qrOpened) showBottomSheet = true

    Scaffold(
        topBar = {
            CheckInTopBar(
                title = "CheckIn24",
                actionIcon = painterResource(R.drawable.outline_settings_24),
                contentDescription = "settings button"
            ) {
                setQrOpened()
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
                    showBottomSheet = true
                }
            )
        }
    ) { contentPadding ->

        Column(
            modifier = modifier.padding(contentPadding)
        ) {
            Column (
                modifier = modifier.padding(horizontal = 16.dp, vertical = 0.dp)
            ) {
                Text(
                    text = "Let's Go, $firstName",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                        .padding(top = 54.dp, bottom = 20.dp)
                )
                CheckInTracker()

                Button(
                    onClick = { },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Check In History")
                }
                Button(
                    onClick = { },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Manage Account ")
                    Icon(
                        painter = painterResource(R.drawable.open_in_new_24px),
                        contentDescription = null,
                        modifier = modifier
                            .size(20.dp)
                    )
                }
            }

        }
        if (showBottomSheet) {
            setQrOpened()
            QrScreen(mbrId, qrMaxBrightness) { showBottomSheet = false }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CheckIn24Theme {
        GreetingScreen(
            "1", "Sergio",
            qrOpened = false,
            navController = rememberNavController(),
            qrOnOpen = false,
            qrMaxBrightness = false,
            setQrOpened = { }
        )
    }
}