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

import android.app.Activity
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.secam.checkin24.R
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun GreetingScreen(
    mbrId: String,
    firstName: String,
    qrOnOpen: Boolean,
    qrOpened: Boolean,
    qrMaxBrightness: Boolean,
    prefsRead: Boolean,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    setQrOpened: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(true)
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    if(prefsRead && !qrOpened) showBottomSheet = qrOnOpen

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CheckIn24") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                actions = {
                    IconButton(onClick = { navController.navigate(CheckInScreen.Settings.name) }) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "settings button"
                        )
                    }

                },
                modifier = modifier,
            )
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
            Text(
                text = "Let's Go, $firstName",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .padding(start = 16.dp, top = 54.dp, bottom = 20.dp)
            )
            CheckInTracker()


        }
        if (showBottomSheet) {
            setQrOpened()
            if (qrMaxBrightness) ForceBrightness()
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                QrScreen(mbrId)
            }
        }
    }
}

@Composable
fun CheckInTracker(modifier: Modifier = Modifier) {
    val dayOfWeek = LocalDate.now().dayOfWeek.value
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        modifier = modifier
            .fillMaxWidth()
            .size(130.dp)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = "Days Checked in this Week",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold,
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
            DayButton("S", dayOfWeek == 7)
            DayButton("M", dayOfWeek == 1)
            DayButton("T", dayOfWeek == 2)
            DayButton("W", dayOfWeek == 3)
            DayButton("T", dayOfWeek == 4)
            DayButton("F", dayOfWeek == 5)
            DayButton("S", dayOfWeek == 6)
        }
    }
}

@Composable
fun DayButton(dayName: String, isToday: Boolean, modifier: Modifier = Modifier) {
    var isToggled by remember { mutableStateOf(false) }
    val iconChecked = Icons.Outlined.Check
    val iconUnchecked = Icons.Outlined.Close
    val buttonContentPadding = PaddingValues(0.dp)
    val buttonPadding = PaddingValues(start = 5.dp, end = 5.dp, bottom = 5.dp)
    val buttonSize = 50.dp
    val buttonColorChecked = ButtonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = Color.White,
        disabledContentColor = Color.White
    )
    val buttonColorUnchecked = ButtonColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        disabledContainerColor = Color.White,
        disabledContentColor = Color.White
    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { isToggled = !isToggled },
            contentPadding = buttonContentPadding,
            colors = if (isToggled) buttonColorChecked else buttonColorUnchecked,
            modifier = modifier
                .size(buttonSize)
                .padding(buttonPadding)

        ) {
            Icon(
                imageVector = if (isToggled) iconChecked else iconUnchecked,
                contentDescription = if (isToggled) "Selected icon button" else "Unselected icon button."
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

@Composable
fun ForceBrightness(brightness: Float = 1f) {
    val context = LocalContext.current
    val activity = context as? Activity ?: return
    DisposableEffect(Unit) {
        val attributes = activity.window.attributes
        val originalBrightness = attributes.screenBrightness
        activity.window.attributes = attributes.apply { screenBrightness = brightness }
        onDispose {
            activity.window.attributes = attributes.apply { screenBrightness = originalBrightness }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    CheckIn24Theme {
//        GreetingScreen("1",false, qrOpened = false, navController = rememberNavController())
//    }
//}