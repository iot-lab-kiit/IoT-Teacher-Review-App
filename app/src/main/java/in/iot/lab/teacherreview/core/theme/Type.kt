package `in`.iot.lab.teacherreview.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import `in`.iot.lab.teacherreview.R


/*
Google font library of original guides to the world of typography,
which the Google Fonts team is producing in collaboration with typographic experts
from around the world. Google Fonts Knowledge enables designers and developers of
all skill sets to choose and use type with purpose.

https://fonts.google.com/
*/

val jostFontFamily = FontFamily(
    // Normal
    Font(R.font.jost_regular, FontWeight.Normal),
    Font(R.font.jost_light, FontWeight.Light),
    Font(R.font.jost_medium, FontWeight.Medium),
    Font(R.font.jost_semibold, FontWeight.SemiBold),
    Font(R.font.jost_bold, FontWeight.Bold),
    Font(R.font.jost_extrabold, FontWeight.ExtraBold),
    Font(R.font.jost_black, FontWeight.Black),
    Font(R.font.jost_thin, FontWeight.Thin),
    Font(R.font.jost_extralight, FontWeight.ExtraLight),

    // Italic
    Font(R.font.jost_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.jost_blackitalic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.jost_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.jost_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.jost_extralightitalic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.jost_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.jost_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.jost_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.jost_thinitalic, FontWeight.Thin, FontStyle.Italic)
)

// Set of Material typography styles to start with
val Typography = Typography(

    displayLarge = TextStyle(
        fontFamily = jostFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
    ),
    displayMedium = TextStyle(
        fontFamily = jostFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = jostFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = jostFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = jostFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = jostFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = jostFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = jostFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = jostFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = jostFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = jostFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = jostFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = jostFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = jostFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = jostFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)