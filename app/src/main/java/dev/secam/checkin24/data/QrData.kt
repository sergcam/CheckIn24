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

import kotlinx.serialization.Serializable

/** A data class meant to be converted into json and encoded in a qr code to match the qrcode from the official 24go app
 * @param SR Should equal exactly "24GO"
 * @param MB Member Number prefixed with "MBR"
 * @param DT Unix time stamp in millis
 * @param TP Should equal exactly "P"
 * @param OS Operating System ("Android")
 * @param AP Official 24go app version
 * @param DI Device Advertising ID. for a device with no advertising id this value is "00000000-0000-0000-0000-000000000000"
 */

@Serializable
data class QrData(
    val SR: String,
    val MB: String,
    val DT: Long,
    val TP: String,
    val OS: String,
    val AP: String,
    val DI: String
) {
    constructor(MB: String, DT: Long) : this(
        SR = "24GO",
        MB = MB,
        DT = DT,
        TP = "P",
        OS = "Android",
        AP = "1.78.2", // last updated 8/2/25
        DI = "00000000-0000-0000-0000-000000000000"
    )
}
