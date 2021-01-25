package com.shots.squads_and_shots.network.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.shots.squads_and_shots.network.fireCloudStorage.StorageInterface

class StorageRepository(private val storage: StorageInterface) {

    fun getCollection(collection: String): Task<QuerySnapshot> {
        return storage.getCollection(collection)
    }
}