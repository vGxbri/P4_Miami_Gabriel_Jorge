// Gabriel Almarcha Martínez y Jorge Maqueda Miguel

package com.example.p4_miami_gabriel_jorge

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.NaturePeople

// 1. Tamaños para el efecto mosaico
enum class CardSize {
    SQUARE, TALL, WIDE
}

// 2. Categorías para la BottomBar (con sus iconos oficiales)
enum class MiamiCategory(val title: String, val icon: ImageVector) {
    Sports("Deportes", Icons.Default.SportsSoccer),
    Food("Comida", Icons.Default.Restaurant),
    Buildings("Edificios", Icons.Default.Apartment),
    Nature("Relax", Icons.Default.NaturePeople)
}

// 3. El objeto de datos principal
data class MiamiItem(
    val id: Int,
    val name: String,
    val description: String,
    val category: MiamiCategory,
    val size: CardSize,
    val imageRes: Int
)