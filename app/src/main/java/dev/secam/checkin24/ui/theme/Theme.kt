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

package dev.secam.checkin24.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import dev.secam.checkin24.data.AppColorScheme
import dev.secam.checkin24.data.AppTheme


@Composable
fun CheckIn24Theme(
    appTheme: AppTheme? = AppTheme.System,
    appColorScheme: AppColorScheme? = AppColorScheme.Dynamic,
    pureBlack: Boolean? = false,
    content: @Composable () -> Unit
) {
    val appColorScheme = appColorScheme ?: AppColorScheme.Dynamic
    val pureBlack = pureBlack ?: false
    val darkTheme = when(appTheme) {
        AppTheme.Dark -> true
        AppTheme.Light -> false
        else -> isSystemInDarkTheme()
    }
    val dynamicColor = when(appColorScheme) {
        AppColorScheme.Dynamic -> true
        else -> false
    }
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) {
                if (pureBlack) blackThemeConvert(dynamicDarkColorScheme(context)) else dynamicDarkColorScheme(context)
            } else dynamicLightColorScheme(context)
        }

        darkTheme && pureBlack -> blackThemeConvert(getColorScheme(appColorScheme,true))
        darkTheme -> getColorScheme(appColorScheme,true)
        else -> getColorScheme(appColorScheme,false)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}