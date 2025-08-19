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

package dev.secam.checkin24.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import dev.secam.checkin24.R
import dev.secam.checkin24.ui.theme.CheckIn24Theme

// top bar with action icon
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckInTopBar(title: String, actionIcon: Painter, contentDescription: String, modifier: Modifier = Modifier, action: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        actions = {
            IconButton(onClick = action) {
                Icon(
                    painter = actionIcon,
                    contentDescription = contentDescription
                )
            }
        },
        modifier = modifier
    )
}

// top bar with back button
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckInTopBar(title: String, modifier: Modifier = Modifier, onBack: () -> Unit){
    TopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold
            )
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back_24px),
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        },
        modifier = modifier
    )
}

// top bar with action icon and back button
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckInTopBar(title: String, actionIcon: Painter, contentDescription: String, modifier: Modifier = Modifier, onBack: () -> Unit, action: () -> Unit){
    TopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back_24px),
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        },
        actions = {
            IconButton(onClick = action) {
                Icon(
                    painter = actionIcon,
                    contentDescription = contentDescription
                )
            }
        },
        modifier = modifier
    )
}

@Preview
@Composable
fun TopBarPreview(){
    CheckIn24Theme {
        Scaffold (
            topBar = {
                CheckInTopBar(
                    title = "CheckIn24",
                    actionIcon = painterResource(R.drawable.ic_settings_24px),
                    contentDescription = stringResource(R.string.settings_button)
                ) {

                }
            }
        ){ contentPadding ->
            Column(
                modifier = Modifier.padding(contentPadding),
        ){  }
        }
    }
}