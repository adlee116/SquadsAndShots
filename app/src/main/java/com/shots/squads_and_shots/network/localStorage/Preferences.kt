package com.shots.squads_and_shots.network.localStorage

import android.content.SharedPreferences
import com.google.gson.Gson

class Preferences(
    prefs: SharedPreferences,
    gson: Gson
) : BasePreferences(prefs, gson) {

    companion object {
        const val FILE_NAME = "StorageDataRepository"
    }
}