// Gabriel Almarcha Martínez y Jorge Maqueda Miguel

package com.example.p4_miami_gabriel_jorge

import androidx.annotation.DrawableRes

// 1. Tamaños para el efecto mosaico
enum class CardSize {
    SQUARE,
    TALL,
    WIDE
}

// 2. Categorías para la BottomBar (con sus iconos oficiales)
enum class MiamiCategory(
        val title: String,
        @DrawableRes val icon: Int,
        @DrawableRes val iconSelected: Int
) {
    Sports("Deportes", R.drawable.beach_ball, R.drawable.beach_ball_fill),
    Food("Comida", R.drawable.fork_knife, R.drawable.fork_knife_fill),
    Buildings("Edificios", R.drawable.buildings, R.drawable.buildings_fill),
    Nature("Relax", R.drawable.tree_palm, R.drawable.tree_palm_fill)
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
