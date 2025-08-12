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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.secam.checkin24.R
import dev.secam.checkin24.ui.components.SettingsItem
import dev.secam.checkin24.ui.settings.dialogs.LicenseDialog
import dev.secam.checkin24.ui.theme.CheckIn24Theme
import dev.secam.checkin24.util.getAppVersion


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_back_24px),
                            contentDescription = "back button"
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = modifier
                .padding(contentPadding)
        ) {
            val uriHandler = LocalUriHandler.current
            AppInfo(uriHandler)
            AuthorInfo(uriHandler)
        }
    }
}

@Composable
fun AppInfo(uriHandler: UriHandler, modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) }
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(top = 8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.c24circle),
                contentDescription = "app logo",
                modifier = modifier
                    .size(50.dp)
                    .padding(start = 16.dp)
            )
            Text(
                text = "CheckIn24",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = modifier
                    .padding(horizontal = 10.dp)
            )
        }

        Column {
            SettingsItem(
                headlineContent = "Version",
                icon = painterResource(R.drawable.info_24px),
                supportingContent = getAppVersion(LocalContext.current) ?: "null",
            )
            SettingsItem(
                headlineContent = "Source Code",
                icon = painterResource(R.drawable.code_24px),
            ) { uriHandler.openUri("https://github.com/sergcam/CheckIn24") }
            SettingsItem(
                headlineContent = "License",
                icon = painterResource(R.drawable.balance_24px),
                supportingContent = "GPL v3",
            ) { showDialog = true }
        }
    }
    if (showDialog) {
        LicenseDialog { showDialog = false }
    }
}

@Composable
fun AuthorInfo(uriHandler: UriHandler, modifier: Modifier = Modifier) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 8.dp)
        ) {
            Text(
                text = "Author",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = modifier
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }

        Column {
            SettingsItem(
                headlineContent = "Sergio Camacho",
                icon = painterResource(R.drawable.person_24px),
            )
            SettingsItem(
                headlineContent = "Github",
                supportingContent = "sergcam",
                icon = painterResource(R.drawable.github_mark),
            ) { uriHandler.openUri("https://github.com/sergcam/") }
            SettingsItem(
                headlineContent = "Website",
                supportingContent = "secam.dev",
                icon = painterResource(R.drawable.insert_link_24px),
            ) { uriHandler.openUri("https://secam.dev") }
            SettingsItem(
                headlineContent = "Email",
                supportingContent = "sergio@secam.dev",
                icon = painterResource(R.drawable.mail_24px),
            ) { uriHandler.openUri("mailto:sergio@secam.dev") }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutPreview() {
    CheckIn24Theme {
        AboutScreen(rememberNavController())
    }
}