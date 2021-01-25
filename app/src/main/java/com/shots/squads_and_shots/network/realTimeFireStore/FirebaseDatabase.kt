package com.shots.squads_and_shots.network.realTimeFireStore

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseDatabase: DatabaseInterface {

    private val databaseReference = Firebase.database

    override fun getReference(path: String) : DatabaseReference {
        return databaseReference.getReference(path)
    }

    override fun postToDatabase(path: String, id: String, postValue: Any) {
        val reference = getReference(path)
        reference.child(path).child(id).setValue(postValue)
    }

    companion object {
        val ROOM = "room"
        val PLAYERS = "players"
        val RULES = "rules"
    }


}