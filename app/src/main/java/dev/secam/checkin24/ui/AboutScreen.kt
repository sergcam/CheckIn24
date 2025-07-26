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

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider
import androidx.navigation.NavHostController
//import androidx.navigation.compose.rememberNavController
import dev.secam.checkin24.R
//import dev.secam.checkin24.ui.theme.CheckIn24Theme

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
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
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
fun AuthorInfo(uriHandler: UriHandler, modifier: Modifier = Modifier) {
    OutlinedCard(

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
            ListItem(
                headlineContent = { Text("Sergio Camacho") },

                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null
                    )
                },
                modifier = modifier
                    .clickable(enabled = true, onClick = {})
                    .fillMaxWidth()
                    .padding()
            )
            ListItem(
                headlineContent = { Text("Github") },
                supportingContent = { Text("sergcam") },
                leadingContent = {
                    Icon(
                        painter = painterResource(R.drawable.github_mark),
                        contentDescription = null,
                        modifier = modifier
                            .size(24.dp)
                    )
                },
                modifier = modifier
                    .clickable(
                        enabled = true,
                        onClick = { uriHandler.openUri("https://github.com/sergcam/") })
                    .fillMaxWidth()
                    .padding()
            )
            ListItem(
                headlineContent = { Text("Website") },
                supportingContent = { Text("secam.dev") },
                leadingContent = {
                    Icon(
                        painter = painterResource(R.drawable.outline_insert_link_24),
                        contentDescription = null
                    )
                },
                modifier = modifier
                    .clickable(
                        enabled = true,
                        onClick = { uriHandler.openUri("https://secam.dev") }
                    )
                    .fillMaxWidth()
                    .padding()
            )
            ListItem(
                headlineContent = { Text("Email") },
                supportingContent = { Text("sergio@secam.dev") },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = null
                    )
                },
                modifier = modifier
                    .clickable(
                        enabled = true,
                        onClick = { uriHandler.openUri("mailto:sergio@secam.dev") }
                    )
                    .fillMaxWidth()
                    .padding()
            )
        }
    }
}

@Composable
fun AppInfo(uriHandler: UriHandler, modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) }
    OutlinedCard(
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
            ListItem(
                headlineContent = { Text("Version") },
                supportingContent = {
                    Text(
                        text = getAppVersion(LocalContext.current) ?: ""
                    )
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null
                    )
                },
                modifier = modifier
                    .clickable(enabled = true, onClick = {})
                    .fillMaxWidth()
                    .padding()
            )
            ListItem(
                headlineContent = { Text("Source Code") },
                leadingContent = {
                    Icon(
                        painter = painterResource(R.drawable.outline_code_24),
                        contentDescription = null
                    )
                },
                modifier = modifier
                    .clickable(
                        enabled = true,
                        onClick = { uriHandler.openUri("https://github.com/sergcam/CheckIn24") })
                    .fillMaxWidth()
                    .padding()
            )
            ListItem(
                headlineContent = { Text("License") },
                supportingContent = { Text("GPL v3") },
                leadingContent = {
                    Icon(
                        painter = painterResource(R.drawable.outline_balance_24),
                        contentDescription = null
                    )
                },
                modifier = modifier
                    .clickable(
                        enabled = true,
                        onClick = { showDialog = true }
                    )
                    .fillMaxWidth()
                    .padding()
            )
        }
    }
    if (showDialog) {
        LicenseDialog { showDialog = false }
    }
}

@Composable
fun LicenseDialog(modifier: Modifier = Modifier, onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(.25f)
        Card(
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End,
                modifier = modifier.padding(horizontal = 26.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = modifier.padding(top = 20.dp)
                ) {
                    Text(
                        text = "License",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = modifier.padding(bottom = 26.dp),
                    )
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .size(300.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.gplv3),
                            modifier = modifier
                                .verticalScroll(rememberScrollState())
                        )
                    }
                }
                Row(
                    modifier = modifier
                        .padding(top = 12.dp, bottom = 14.dp)
                ) {
                    TextButton(
                        onClick = { onDismissRequest() }
                        ) {
                        Text("Close")
                    }
                }
            }
        }
    }
}


fun getAppVersion(
    context: Context,
): String? {
    return try {
        val packageManager = context.packageManager
        val packageName = context.packageName
        val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
        } else {
            packageManager.getPackageInfo(packageName, 0)
        }
        packageInfo.versionName

    } catch (_: Exception) {
        null
    }
}

//@Preview(showBackground = true)
//@Composable
//fun AboutPreview() {
//    CheckIn24Theme {
//        AboutScreen(rememberNavController())
//    }
//}