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
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class HistoryRepoImpl  @Inject constructor(private val checkInDao: CheckInDao): HistoryRepo {
    override fun getAllEntriesStream(): Flow<Map<LocalDate, List<LocalTime>>> {
        val flow = checkInDao.getAllEntries().map { entries ->
            val checkInData = mutableMapOf<LocalDate, List<LocalTime>>()
            for(entry in entries) {
                checkInData.put(
                    LocalDate.parse(entry.date),
                    Json.decodeFromString<List<String>>(entry.timeList).map {string ->
                        LocalTime.parse(string)
                    }
                )
            }
            checkInData as Map<LocalDate, List<LocalTime>>
        }
        return flow
    }

    override fun getEntryStream(date: LocalDate): Flow<CheckInData?> =
        checkInDao.getEntry(date.toString()).map { entry ->
            val date = LocalDate.parse(entry.date)
            val timeList = Json.decodeFromString<List<String>>(entry.timeList).map {string ->
                LocalTime.parse(string)
            }
            CheckInData(date, timeList)
        }

    override suspend fun insertEntry(checkInData: CheckInData) {
        val key = checkInData.date.toString()
        val stringList = checkInData.timeList.map { time ->
            time.toString()
        }
        val value = Json.encodeToString(stringList)
        val entry = CheckInEntry(key, value)
        checkInDao.insert(entry)
    }

    override suspend fun deleteEntry(date: LocalDate) {
        checkInDao.deleteDate(date.toString())
    }

    override suspend fun updateEntry(checkInData: CheckInData) {
        val key = checkInData.date.toString()
        val stringList = checkInData.timeList.map { time ->
            time.toString()
        }
        val value = Json.encodeToString(stringList)
        val entry = CheckInEntry(key, value)
        checkInDao.update(entry)
    }

    override suspend fun deleteAll() {
        checkInDao.deleteAll()
    }
}