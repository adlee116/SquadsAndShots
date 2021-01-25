package com.shots.squads_and_shots.network.fireCloudStorage

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseStorage: StorageInterface {

        private val fireStore = Firebase.firestore

        override fun getCollection(collection: String): Task<QuerySnapshot> {
                return fireStore.collection(collection).get()
        }
}