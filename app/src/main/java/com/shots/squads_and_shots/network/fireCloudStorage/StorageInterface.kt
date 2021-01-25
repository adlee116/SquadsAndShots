package com.shots.squads_and_shots.network.fireCloudStorage

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface StorageInterface {

    fun getCollection(collection: String): Task<QuerySnapshot>

}