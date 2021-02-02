package com.shots.squads_and_shots.network.localStorage

interface LocalStorageInterface {

    fun getUniqueUserId(): String?

    fun setUniqueUserId(uniqueId: String)
}