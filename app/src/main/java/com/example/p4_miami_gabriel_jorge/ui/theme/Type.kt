package com.example.p4_miami_gabriel_jorge.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.p4_miami_gabriel_jorge.R

val ChillaxFamily =
    FontFamily(
        Font(R.font.chillax_extralight, FontWeight.ExtraLight),
        Font(R.font.chillax_light, FontWeight.Light),
        Font(R.font.chillax_regular, FontWeight.Normal),
        Font(R.font.chillax_medium, FontWeight.Medium),
        Font(R.font.chillax_semibold, FontWeight.SemiBold),
        Font(R.font.chillax_bold, FontWeight.Bold)
    )

val SynonymFamily =
    FontFamily(
        Font(R.font.synonym_extralight, FontWeight.ExtraLight),
        Font(R.font.synonym_light, FontWeight.Light),
        Font(R.font.synonym_regular, FontWeight.Normal),
        Font(R.font.synonym_medium, FontWeight.Medium),
        Font(R.font.synonym_semibold, FontWeight.SemiBold),
        Font(R.font.synonym_bold, FontWeight.Bold)
    )

val Typography =
    Typography(
        displayLarge =
            TextStyle(
                fontFamily = ChillaxFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 57.sp,
                lineHeight = 64.sp
            ),
        headlineLarge =
            TextStyle(
                fontFamily = ChillaxFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                lineHeight = 40.sp
            ),
        headlineMedium =
            TextStyle(
                fontFamily = ChillaxFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                lineHeight = 36.sp
            ),
        headlineSmall =
            TextStyle(
                fontFamily = ChillaxFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp,
                lineHeight = 32.sp
            ),
        titleLarge =
            TextStyle(
                fontFamily = SynonymFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                lineHeight = 28.sp
            ),
        bodyLarge =
            TextStyle(
                fontFamily = SynonymFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp
            ),
        bodyMedium =
            TextStyle(
                fontFamily = SynonymFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.25.sp
            ),
        labelSmall =
            TextStyle(
                fontFamily = SynonymFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.5.sp
            )
    )
