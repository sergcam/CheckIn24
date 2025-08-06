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

package dev.secam.checkin24.util

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext

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