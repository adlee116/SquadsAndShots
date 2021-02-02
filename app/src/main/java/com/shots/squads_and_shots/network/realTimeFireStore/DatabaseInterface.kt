package com.shots.squads_and_shots.network.realTimeFireStore

import com.google.firebase.database.DatabaseReference

interface DatabaseInterface {

    fun getReference(path: String) : DatabaseReference

    fun postToDatabase(path: String, id: String, postValue: Any)

    companion object {
        val ROOM = "room"
        val PLAYERS = "players"
        val RULES = "rules"
        val DRINKS = "drinks"
    }
}