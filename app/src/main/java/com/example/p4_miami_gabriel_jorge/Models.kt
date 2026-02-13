// Gabriel Almarcha Martínez y Jorge Maqueda Miguel

package com.example.p4_miami_gabriel_jorge

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

// 2. Categorías para la BottomBar (con sus iconos oficiales)
enum class MiamiCategory(
        @StringRes val titleRes: Int,
        @DrawableRes val icon: Int,
        @DrawableRes val iconSelected: Int
) {
        Sports(R.string.category_sports, R.drawable.beach_ball, R.drawable.beach_ball_fill),
        Food(R.string.category_food, R.drawable.fork_knife, R.drawable.fork_knife_fill),
        Buildings(R.string.category_buildings, R.drawable.buildings, R.drawable.buildings_fill),
        Nature(R.string.category_nature, R.drawable.tree_palm, R.drawable.tree_palm_fill)
}

// 3. El objeto de datos principal
data class MiamiItem(
        val id: Int,
        val name: String,
        val description: String,
        val category: MiamiCategory,
        val imageRes: Int,
        val aspectRatio: Float = 1f,
        val sponsored: Boolean = false,
        val type: String
)
