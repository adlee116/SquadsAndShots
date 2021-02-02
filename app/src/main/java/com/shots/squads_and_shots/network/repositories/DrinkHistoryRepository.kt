package com.shots.squads_and_shots.network.repositories

import com.shots.squads_and_shots.network.models.DrinkOccasion
import com.shots.squads_and_shots.network.realTimeFireStore.DatabaseInterface
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.ListenerRequest

class DrinkHistoryRepository(private val database: DatabaseInterface) {

    fun submitDrinkRequest(roomCode: String, drink: DrinkOccasion) {
        database.getReference(DatabaseInterface.DRINKS).child(roomCode).push().setValue(drink)
    }

    fun getAllHistory(roomCode: String) {
        database.getReference(DatabaseInterface.DRINKS).child(roomCode).get()
    }

    fun setDrinkListener(request: ListenerRequest) {
        database.getReference(DatabaseInterface.DRINKS).child(request.roomCode).addValueEventListener(request.listener)
    }
}