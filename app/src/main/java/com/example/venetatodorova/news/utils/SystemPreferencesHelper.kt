package com.example.venetatodorova.news.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import com.example.venetatodorova.news.R


object SystemPreferencesHelper {

    fun write(activity: Activity, resources: Resources, sources: Set<String>) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putStringSet(resources.getString(R.string.shared_pref_key), sources)
        editor.apply()
    }

    fun read(activity: Activity, resources: Resources): Set<String>? {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        val defaultValue = setOf(
                "ABC News (AU)",
                "BBC News"
        )
        val selectedSources = sharedPref.getStringSet(resources.getString(R.string.shared_pref_key), defaultValue)
        return selectedSources
    }
}