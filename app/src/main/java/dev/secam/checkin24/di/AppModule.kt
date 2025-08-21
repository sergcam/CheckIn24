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

package dev.secam.checkin24.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.secam.checkin24.data.CheckInDao
import dev.secam.checkin24.data.CheckInDatabase
import dev.secam.checkin24.data.HistoryRepo
import dev.secam.checkin24.data.HistoryRepoImpl
import dev.secam.checkin24.data.PreferencesRepo
import dev.secam.checkin24.data.PreferencesRepoImpl
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "dev.secam.checkin24.user_preferences"
)

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun providePreferencesRepo(
        @ApplicationContext context: Context
    ): PreferencesRepo = PreferencesRepoImpl(context.dataStore)

    @Singleton
    @Provides
    fun provideCheckInDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        CheckInDatabase::class.java,
        "checkin_database"
    ).build()

    @Singleton
    @Provides
    fun provideCheckInDao(checkInDatabase: CheckInDatabase
    ):CheckInDao = checkInDatabase.checkInDao()

    @Provides
    @Singleton
    fun provideHistoryRepo(checkInDao: CheckInDao): HistoryRepo {
        return HistoryRepoImpl(checkInDao)
    }
}