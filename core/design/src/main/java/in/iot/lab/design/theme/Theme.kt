package `in`.iot.lab.design.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val GlassColorScheme = darkColorScheme(
    primary                = primaryColor,
    onPrimary              = onPrimaryColor,
    primaryContainer       = primaryContainerColor,
    onPrimaryContainer     = onPrimaryContainerColor,
    secondary              = secondaryColor,
    onSecondary            = onSecondaryColor,
    secondaryContainer     = secondaryContainerColor,
    onSecondaryContainer   = onSecondaryContainerColor,
    tertiary               = tertiaryColor,
    onTertiary             = onTertiaryColor,
    tertiaryContainer      = tertiaryContainerColor,
    onTertiaryContainer    = onTertiaryContainerColor,
    background             = backgroundColor,
    onBackground           = onBackgroundColor,
    surface                = surfaceColor,
    onSurface              = onSurfaceColor,
    surfaceVariant         = surfaceVariantColor,
    onSurfaceVariant       = onSurfaceVariantColor,
    error                  = errorColor,
    onError                = onErrorColor,
    errorContainer         = errorContainerColor,
    onErrorContainer       = onErrorContainerColor
)

@Composable
fun CustomAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = GlassColorScheme,
        shapes      = Shapes,
        typography  = Typography,
        content     = content
    )
}