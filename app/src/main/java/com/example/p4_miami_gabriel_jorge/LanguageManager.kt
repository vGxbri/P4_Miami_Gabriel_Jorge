// Gabriel Almarcha Martínez y Jorge Maqueda Miguel

package com.example.p4_miami_gabriel_jorge

import android.app.LocaleManager
import android.content.Context
import android.os.LocaleList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class AppLanguage(
    val code: String,
    val displayName: String,
    val flag: String,
    val jsonFileName: String
) {
    ES("es", "Español", "🇪🇸", "miami_data.json"),
    EN("en", "English", "🇬🇧", "miami_data_en.json"),
    FR("fr", "Français", "🇫🇷", "miami_data_fr.json")
}

object LanguageManager {
    private val _currentLanguage = MutableStateFlow(AppLanguage.ES)
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    fun changeLanguage(context: Context, language: AppLanguage) {
        _currentLanguage.value = language
        val localeManager = context.getSystemService(LocaleManager::class.java)
        localeManager.applicationLocales = LocaleList.forLanguageTags(language.code)
    }

    fun initFromSystem(context: Context) {
        val localeManager = context.getSystemService(LocaleManager::class.java)
        val appLocales = localeManager.applicationLocales
        if (!appLocales.isEmpty) {
            val tag = appLocales.get(0)?.language ?: "es"
            _currentLanguage.value = AppLanguage.entries.find { it.code == tag } ?: AppLanguage.ES
        }
    }
}
