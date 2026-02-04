// Gabriel Almarcha Martínez y Jorge Maqueda Miguel

package com.example.p4_miami_gabriel_jorge.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.p4_miami_gabriel_jorge.R

val ChillaxFamily = FontFamily(Font(R.font.chillax_semibold, FontWeight.SemiBold))
val SynonymFamily = FontFamily(Font(R.font.synonym_regular, FontWeight.Normal))

val Typography = Typography(
    // Títulos (Usan Chillax)
    titleLarge = TextStyle(
        fontFamily = ChillaxFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp
    ),
    // Descripciones (Usan Synonym)
    bodyMedium = TextStyle(
        fontFamily = SynonymFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = SynonymFamily,
        fontSize = 16.sp
    )
)