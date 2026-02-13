// Gabriel Almarcha Martínez y Jorge Maqueda Miguel

package com.example.p4_miami_gabriel_jorge

import android.app.Application
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MiamiViewModel(application: Application) : AndroidViewModel(application) {
    private val _selectedCategory = MutableStateFlow(MiamiCategory.Sports)
    val selectedCategory: StateFlow<MiamiCategory> = _selectedCategory.asStateFlow()

    private val _allItems = mutableListOf<MiamiItem>()
    val allItems: List<MiamiItem>
        get() = _allItems

    private val _filteredItems = MutableStateFlow<List<MiamiItem>>(emptyList())
    val filteredItems: StateFlow<List<MiamiItem>> = _filteredItems.asStateFlow()

    private val _currentLanguage = MutableStateFlow(AppLanguage.ES)
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    init {
        // Inicializa el gestor de idiomas y carga los datos iniciales
        LanguageManager.initFromSystem(application.applicationContext)
        _currentLanguage.value = LanguageManager.currentLanguage.value
        loadDataFromJSON()
        updateCategory(MiamiCategory.Sports)
    }

    /**
     * Carga y procesa los datos desde un archivo JSON.
     * Mapea los datos JSON a objetos MiamiItem.
     */
    private fun loadDataFromJSON() {
        val context = getApplication<Application>().applicationContext
        val jsonFileName = _currentLanguage.value.jsonFileName

        val jsonString = context.assets.open(jsonFileName).bufferedReader().use { it.readText() }

        val gson = Gson()
        val itemType = object : TypeToken<List<MiamiItemJSON>>() {}.type
        val jsonItems: List<MiamiItemJSON> = gson.fromJson(jsonString, itemType)

        // Mapeo a MiamiItem y cálculo de relación de aspecto de las imágenes
        val mappedItems =
            jsonItems.map { jsonItem ->
                val imageRes =
                    context.resources.getIdentifier(
                        jsonItem.imageName,
                        "drawable",
                        context.packageName
                    )
                        .let { if (it == 0) R.drawable.intermiami else it }

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
        _allItems.clear()
        _allItems.addAll(mappedItems.sortedByDescending { it.sponsored })
    }

    /**
     * Cambia el idioma global de la aplicación, recarga los datos y actualiza la vista.
     */
    fun changeLanguage(language: AppLanguage) {
        val context = getApplication<Application>().applicationContext
        _currentLanguage.value = language
        LanguageManager.changeLanguage(context, language)
        loadDataFromJSON()
        updateCategory(_selectedCategory.value)
    }

    /**
     * Actualiza la categoría seleccionada y filtra la lista de elementos mostrada.
     */
    fun updateCategory(newCategory: MiamiCategory) {
        _selectedCategory.value = newCategory
        _filteredItems.value = _allItems.filter { it.category == newCategory }
    }

    fun getItemById(id: Int): MiamiItem? {
        return allItems.find { it.id == id }
    }
}

data class MiamiItemJSON(
    val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val imageName: String,
    val sponsored: Boolean = false,
    val type: String
)
