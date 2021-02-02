package com.shots.squads_and_shots.network.localStorage

import java.util.*

class LocalStorageRepository(private val storageLocation: LocalStorageInterface) {

    fun getOrCreateUserId(): String {
        val id = getUniqueUserId()
        return id ?: run {
            val newCode = generateUniqueUserCode()
            setUniqueUserId(newCode)
            newCode
        }
    }

    fun getUniqueUserId(): String? {
        return storageLocation.getUniqueUserId()
    }

    fun setUniqueUserId(uniqueId: String) {
        storageLocation.setUniqueUserId(uniqueId)
    }

    fun generateUniqueUserCode(): String {
        return UUID.randomUUID().toString()
    }

    companion object {
        const val UNIQUE_USER_ID = "uniqueUserId"
    }
}