package com.example.pr12_battletanks_ustinova_d_v_

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.drawable.DrawableContainer
import com.example.pr12_battletanks_ustinova_d_v_.models.Element
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

const val KEY_LEVEL = "key_level"

class LevelStorage( context: Context) {
    private val prefs = (context as Activity).getPreferences(MODE_PRIVATE)
    private val gson = Gson()
    fun saveLevel(elementsOnContainer: List<Element>) {
        prefs.edit()
            .putString(KEY_LEVEL, Gson().toJson(elementsOnContainer))
            .apply()
    }

    fun loadLevel():List<Element>? {
        val levelFromPrefs = prefs.getString(KEY_LEVEL, null) ?: return null
        val type = object : TypeToken<List<Element>>() {}.type
        return gson.fromJson(levelFromPrefs, type)
    }
}