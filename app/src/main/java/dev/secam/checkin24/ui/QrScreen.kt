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

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LifecycleResumeEffect
import dev.secam.checkin24.R
import dev.secam.checkin24.composeqr.QrCodeView
import dev.secam.checkin24.data.QrData
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrScreen(mbrID: String, modifier: Modifier = Modifier) {
    val logo = painterResource(R.drawable.logo_24f)
    var timestamp by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var json by remember { mutableStateOf(Json.encodeToString(QrData(MB = mbrID, DT = timestamp))) }
    var isActive by remember { mutableStateOf(true) }

    LifecycleResumeEffect(Unit) {
        isActive = true
        onPauseOrDispose {
            isActive = false
        }
    }
    //update qr code if outdated
    if(isActive){
        LaunchedEffect(json) {
            while ((System.currentTimeMillis() - timestamp) < 300000) { // 5 minutes
                delay(5000) // check time every 5 sec
            }
            timestamp = System.currentTimeMillis()
            json = Json.encodeToString(QrData(MB = mbrID, DT = timestamp))

        }
    }
    Column(
        modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = "Let's make today count.",
            fontSize = 26.sp,
            modifier = modifier
                .padding(bottom = 1.dp)
        )
        Text(
            text = "One scan to check in and earn points",
            fontSize = 14.sp,
            modifier = modifier
                .padding(bottom = 18.dp)
        )
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .background(color = MaterialTheme.colorScheme.surfaceContainerHigh)
                    .padding(32.dp)
            ) {
                Box(
                    modifier
                        .background(color = Color.White)
                        .padding(6.dp)
                ) {
                    QrCodeView(
                        data = json,
                        modifier = modifier
                            .aspectRatio(1f)
                    ) {
                        Image(
                            painter = logo,
                            contentDescription = null
                        )
                    }
                }
                Text(
                    text = mbrID,
                    fontSize = 22.sp,
                    modifier = modifier
                        .padding(top = 18.dp)
                )
            }
        }
        Text(
            text = "FitPerks points may take 24 hours to be added.",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = modifier
                .padding(top = 18.dp)
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun QrPreview() {
//    CheckIn24Theme {
//        QrScreen("MBR00000000")
//    }
//}

