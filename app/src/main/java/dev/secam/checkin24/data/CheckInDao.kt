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

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CheckInDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entry: CheckInEntry)

    @Update
    suspend fun update(entry: CheckInEntry)

    @Delete
    suspend fun delete(entry: CheckInEntry)

    @Query("SELECT * from check_ins WHERE date = :date")
    fun getEntry(date: String): Flow<CheckInEntry>

    @Query("SELECT * from check_ins")
    fun getAllEntries(): Flow<List<CheckInEntry>>

    @Query("DELETE FROM check_ins WHERE date = :date")
    suspend fun deleteDate(date: String)

    @Query("DELETE FROM check_ins")
    suspend fun deleteAll()
}