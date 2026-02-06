// Gabriel Almarcha Martínez y Jorge Maqueda Miguel

package com.example.p4_miami_gabriel_jorge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.p4_miami_gabriel_jorge.ui.theme.P4_Miami_Gabriel_JorgeTheme

// --- VIEWMODEL: LA LÓGICA DE NEGOCIO ---
class MiamiViewModel : ViewModel() {
    // Estado privado, exposición pública (Encapsulamiento)
    private val _selectedCategory = MutableStateFlow(MiamiCategory.Sports)
    val selectedCategory: StateFlow<MiamiCategory> = _selectedCategory.asStateFlow()

    // Datos simulados (En un caso real vendrían de un Repositorio)
    val allItems = listOf(
        MiamiItem(
            1,
            "Inter Miami",
            "El equipo de Messi.",
            MiamiCategory.Sports,
            CardSize.WIDE,
            R.drawable.intermiami
        ),
        MiamiItem(
            2,
            "Miami Heat",
            "NBA en estado puro.",
            MiamiCategory.Sports,
            CardSize.TALL,
            R.drawable.intermiami
        ),
        MiamiItem(
            3,
            "Sándwich Cubano",
            "El mejor de la Calle Ocho.",
            MiamiCategory.Food,
            CardSize.SQUARE,
            R.drawable.intermiami
        ),
        MiamiItem(
            4,
            "Ceviche Tropical",
            "Fresco y cítrico.",
            MiamiCategory.Food,
            CardSize.TALL,
            R.drawable.intermiami
        ),
        MiamiItem(
            5,
            "Freedom Tower",
            "Historia de la inmigración.",
            MiamiCategory.Buildings,
            CardSize.TALL,
            R.drawable.intermiami
        ),
        MiamiItem(
            6,
            "South Beach",
            "Palmeras y sol.",
            MiamiCategory.Nature,
            CardSize.SQUARE,
            R.drawable.intermiami
        )
    )

    fun updateCategory(newCategory: MiamiCategory) {
        _selectedCategory.value = newCategory
    }

    fun getFilteredItems(category: MiamiCategory): List<MiamiItem> {
        return allItems.filter { it.category == category }
    }

    fun getItemById(id: Int): MiamiItem? {
        return allItems.find { it.id == id }
    }
}