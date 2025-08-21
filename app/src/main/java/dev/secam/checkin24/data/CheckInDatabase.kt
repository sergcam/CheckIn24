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

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CheckInEntry::class], version = 1, exportSchema = false)
abstract class CheckInDatabase: RoomDatabase() {
    abstract fun checkInDao(): CheckInDao
    companion object {
        @Volatile
        private var Instance: CheckInDatabase? = null
        fun getDatabase(context: Context): CheckInDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, CheckInDatabase::class.java, "checkin_database")
                    .fallbackToDestructiveMigration(false)
                    .build()
                    .also { Instance = it }
            }
        }
    }

}