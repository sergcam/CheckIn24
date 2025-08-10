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

package dev.secam.checkin24.data

import kotlinx.coroutines.flow.Flow

interface PreferencesRepo {
    val preferencesFlow: Flow<UserPreferences>

    suspend fun saveMbrId(mbrId: String)
    suspend fun saveFirstName(firstName: String)

    suspend fun saveThemePref(theme: AppTheme)

    suspend fun saveColorSchemePref(colorScheme: AppColorScheme)

    suspend fun saveQrOnOpenPref(qrOnOpen: Boolean)

    suspend fun saveQrMaxBrightnessPref(qrMaxBrightness: Boolean)

    suspend fun savePureBlackPref(pureBlack: Boolean)

    suspend fun saveNtpPref(useNtp: Boolean)
    suspend fun saveNtpServ(ntpServer: String)
}