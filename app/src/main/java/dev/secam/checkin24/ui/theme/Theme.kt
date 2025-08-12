package dev.secam.checkin24.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import dev.secam.checkin24.data.AppColorScheme
import dev.secam.checkin24.data.AppTheme

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun CheckIn24Theme(
    appTheme: AppTheme? = AppTheme.System,
    appColorScheme: AppColorScheme? = AppColorScheme.Dynamic,
    pureBlack: Boolean? = false,
    content: @Composable () -> Unit
) {
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
                if (pureBlack) BlackThemeConvert(dynamicDarkColorScheme(context)) else dynamicDarkColorScheme(context)
            } else dynamicLightColorScheme(context)
        }

        darkTheme && pureBlack -> BlackThemeConvert(DarkColorScheme)
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}