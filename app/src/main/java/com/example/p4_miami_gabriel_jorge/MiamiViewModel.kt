package com.example.p4_miami_gabriel_jorge

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Cambiamos a AndroidViewModel para tener acceso a los assets
class MiamiViewModel(application: Application) : AndroidViewModel(application) {

    private val _selectedCategory = MutableStateFlow(MiamiCategory.Sports)
    val selectedCategory: StateFlow<MiamiCategory> = _selectedCategory.asStateFlow()

    // Esta será nuestra lista cargada desde el JSON
    private val _allItems = mutableListOf<MiamiItem>()
    val allItems: List<MiamiItem> get() = _allItems

    init {
        loadDataFromJSON()
    }

    private fun loadDataFromJSON() {
        val context = getApplication<Application>().applicationContext

        // 1. Leer el archivo JSON desde Assets
        val jsonString = context.assets.open("miami_data.json").bufferedReader().use { it.readText() }

        // 2. Usar Gson para convertir el texto en una lista de objetos temporales
        val gson = Gson()
        val itemType = object : TypeToken<List<MiamiItemJSON>>() {}.type
        val jsonItems: List<MiamiItemJSON> = gson.fromJson(jsonString, itemType)

        // 3. Convertir los datos del JSON al modelo MiamiItem que usa la App
        // Dentro de tu función loadDataFromJSON()
        val mappedItems = jsonItems.map { jsonItem ->
            MiamiItem(
                id = jsonItem.id,
                name = jsonItem.name,
                description = jsonItem.description,
                category = MiamiCategory.valueOf(jsonItem.category),
                size = CardSize.valueOf(jsonItem.size),
                // Esto buscará una imagen en drawable con el nombre que pusimos en el JSON
                imageRes = context.resources.getIdentifier(
                    jsonItem.imageName, "drawable", context.packageName
                ).let { if (it == 0) R.drawable.intermiami else it } // Si no la encuentra, pone una por defecto
            )
        }
        _allItems.addAll(mappedItems)
    }

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

// Clase temporal para leer el JSON (los nombres deben coincidir con el archivo .json)
data class MiamiItemJSON(
    val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val size: String,
    val imageName: String
)