package com.shots.squads_and_shots.network.localStorage

class SharedPreferencesStorage(private val preferences: Preferences): LocalStorageInterface {

    override fun getUniqueUserId(): String? {
        return preferences.getNullableString(LocalStorageRepository.UNIQUE_USER_ID)
    }

    override fun setUniqueUserId(uniqueId: String) {
        preferences.insert(LocalStorageRepository.UNIQUE_USER_ID, uniqueId)
    }


}