package com.example.p4_miami_gabriel_jorge

import android.app.Application
import android.graphics.BitmapFactory
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
    val allItems: List<MiamiItem>
        get() = _allItems

    private val _filteredItems = MutableStateFlow<List<MiamiItem>>(emptyList())
    val filteredItems: StateFlow<List<MiamiItem>> = _filteredItems.asStateFlow()

    init {
        loadDataFromJSON()
        updateCategory(MiamiCategory.Sports)
    }

    private fun loadDataFromJSON() {
        val context = getApplication<Application>().applicationContext

        // 1. Leer el archivo JSON desde Assets
        val jsonString =
                context.assets.open("miami_data.json").bufferedReader().use { it.readText() }

        // 2. Usar Gson para convertir el texto en una lista de objetos temporales
        val gson = Gson()
        val itemType = object : TypeToken<List<MiamiItemJSON>>() {}.type
        val jsonItems: List<MiamiItemJSON> = gson.fromJson(jsonString, itemType)

        // 3. Convertir los datos del JSON al modelo MiamiItem que usa la App
        // Dentro de tu función loadDataFromJSON()
        val mappedItems =
                jsonItems.map { jsonItem ->
                    // Resolver recurso drawable
                    val imageRes =
                            context.resources.getIdentifier(
                                            jsonItem.imageName,
                                            "drawable",
                                            context.packageName
                                    )
                                    .let { if (it == 0) R.drawable.intermiami else it }

                    // Obtener dimensiones reales sin cargar el bitmap en memoria
                    val opts = BitmapFactory.Options().apply { inJustDecodeBounds = true }
                    BitmapFactory.decodeResource(context.resources, imageRes, opts)
                    val aspectRatio =
                            if (opts.outHeight > 0)
                                    opts.outWidth.toFloat() / opts.outHeight.toFloat()
                            else 1f

                    MiamiItem(
                            id = jsonItem.id,
                            name = jsonItem.name,
                            description = jsonItem.description,
                            category = MiamiCategory.valueOf(jsonItem.category),
                            imageRes = imageRes,
                            aspectRatio = aspectRatio,
                            sponsored = jsonItem.sponsored,
                            type = jsonItem.type
                    )
                }
        // Ordenamos: primero los sponsored, luego el resto
        _allItems.addAll(mappedItems.sortedByDescending { it.sponsored })
    }

    fun updateCategory(newCategory: MiamiCategory) {
        _selectedCategory.value = newCategory
        _filteredItems.value = _allItems.filter { it.category == newCategory }
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
        val imageName: String,
        val sponsored: Boolean = false,
        val type: String
)
