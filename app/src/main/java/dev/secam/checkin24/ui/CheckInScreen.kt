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

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class CheckInScreen() {
    Greeting(),
    Settings(),
    About()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckInApp(
    modifier: Modifier = Modifier,
    viewModel: CheckInViewModel,
    navController: NavHostController = rememberNavController()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val mbrId = uiState.mbrId
    val firstName = uiState.firstName
    val theme = uiState.theme
    val colorScheme = uiState.colorScheme
    val qrOnOpen = uiState.qrOnOpen
    val qrMaxBrightness = uiState.qrMaxBrightness
    val pureBlack = uiState.pureBlack
    val qrOpened = uiState.qrOpened
    val prefsRead = uiState.prefsRead

    NavHost(
        navController = navController,
        startDestination = CheckInScreen.Greeting.name,
        popExitTransition = {
            scaleOut(
                targetScale = 0.9f,
                transformOrigin = TransformOrigin(pivotFractionX = 0.5f, pivotFractionY = 0.5f)
            )
        },
        popEnterTransition = {
            EnterTransition.None
        },
        modifier = modifier.padding()
    ) {
        composable(
            route = CheckInScreen.Greeting.name,
            enterTransition = {
                return@composable fadeIn(tween(1000))
            }, exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }, popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            }) {
            GreetingScreen(
                mbrId,
                firstName,
                qrOnOpen,
                qrOpened,
                qrMaxBrightness,
                prefsRead,
                navController
            ) { viewModel.setQrOpened(true) }
        }
        composable(
            route = CheckInScreen.Settings.name,
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            },
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            }

        ) {
            SettingsScreen(
                mbrId = mbrId,
                firstName = firstName,
                theme = theme,
                colorScheme = colorScheme,
                qrOnOpen = qrOnOpen,
                qrMaxBrightness = qrMaxBrightness,
                pureBlack = pureBlack,
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(
            route = CheckInScreen.About.name,
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            }
        ) {
            AboutScreen(navController)
        }
    }
}

