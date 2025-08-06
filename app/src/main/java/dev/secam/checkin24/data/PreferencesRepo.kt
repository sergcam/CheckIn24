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

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

data class UserPreferences(
    val mbrId: String = "",
    val firstName: String = "",
    val theme: String = "",
    val colorScheme: String = "",
    val qrOnOpen: Boolean = false,
    val qrMaxBrightness: Boolean = false,
    val pureBlack: Boolean = false,
    val useNtp: Boolean = false,
    val ntpServer: String = ""
)

class PreferencesRepo(private val dataStore: DataStore<Preferences>) {
    private companion object {
        val MBR_ID = stringPreferencesKey("mbr_id")
        val FIRST_NAME = stringPreferencesKey("first_name")
        val THEME = stringPreferencesKey("theme")
        val COLOR_SCHEME = stringPreferencesKey("color_scheme")
        val QR_ON_OPEN = booleanPreferencesKey("qr_on_open")
        val QR_MAX_BRIGHTNESS = booleanPreferencesKey("qr_max_brightness")
        val PURE_BLACK = booleanPreferencesKey("pure_black")
        val USE_NTP = booleanPreferencesKey("use_ntp")
        val NTP_SERVER = stringPreferencesKey("ntp_server")
        const val TAG = "PreferencesRepo"
    }

    val preferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            Log.d(TAG, "prefs read")
            val mbrId = preferences[MBR_ID] ?: ""
            val firstName = preferences[FIRST_NAME] ?: ""
            val theme = preferences[THEME] ?: "system"
            val colorScheme = preferences[COLOR_SCHEME] ?: "dynamic"
            val qrOnOpen = preferences[QR_ON_OPEN] ?: false
            val qrMaxBrightness = preferences[QR_MAX_BRIGHTNESS] ?: true
            val pureBlack = preferences[PURE_BLACK] ?: false
            val useNtp = preferences[USE_NTP] ?: false
            val ntpServer = preferences[NTP_SERVER] ?: "pool.ntp.org"
            UserPreferences(
                mbrId = mbrId,
                firstName = firstName,
                theme = theme,
                colorScheme = colorScheme,
                qrOnOpen = qrOnOpen,
                qrMaxBrightness = qrMaxBrightness,
                pureBlack = pureBlack,
                useNtp = useNtp,
                ntpServer = ntpServer
            )

        }

    suspend fun saveMbrId(mbrId: String) {
        dataStore.edit { preferences ->
            preferences[MBR_ID] = mbrId
        }
    }

    suspend fun saveFirstName(firstName: String) {
        dataStore.edit { preferences ->
            preferences[FIRST_NAME] = firstName
        }
    }

    suspend fun saveThemePref(theme: String) {
        dataStore.edit { preferences ->
            preferences[THEME] = theme
        }
    }

    suspend fun saveColorSchemePref(colorScheme: String) {
        dataStore.edit { preferences ->
            preferences[COLOR_SCHEME] = colorScheme
        }
    }

    suspend fun saveQrOnOpenPref(qrOnOpen: Boolean) {
        Log.d(TAG, "saved QrOnOpen as $qrOnOpen")
        dataStore.edit { preferences ->
            preferences[QR_ON_OPEN] = qrOnOpen
        }
    }

    suspend fun saveQrMaxBrightnessPref(qrMaxBrightness: Boolean) {
        dataStore.edit { preferences ->
            preferences[QR_MAX_BRIGHTNESS] = qrMaxBrightness
        }
    }

    suspend fun savePureBlackPref(pureBlack: Boolean) {
        dataStore.edit { preferences ->
            preferences[PURE_BLACK] = pureBlack
        }
    }

    suspend fun saveNtpPref(useNtp: Boolean) {
        dataStore.edit { preferences ->
            preferences[USE_NTP] = useNtp
        }
    }
    suspend fun saveNtpServ(ntpServer: String) {
        dataStore.edit { preferences ->
            preferences[NTP_SERVER] = ntpServer
        }
    }
}

