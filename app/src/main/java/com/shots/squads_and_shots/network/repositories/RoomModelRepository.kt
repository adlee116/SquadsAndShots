package com.shots.squads_and_shots.network.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.shots.squads_and_shots.network.realTimeFireStore.DatabaseInterface
import com.shots.squads_and_shots.network.realTimeFireStore.FirebaseDatabase
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.ListenerRequest
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.Player
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.RoomModel
import com.shots.squads_and_shots.presentation.models.GeneralRule
import com.shots.squads_and_shots.presentation.models.NominatedRule
import com.shots.squads_and_shots.presentation.models.Rules
import com.shots.squads_and_shots.presentation.models.SecretTasks

class RoomModelRepository(private val database: DatabaseInterface) {

    fun createRoom(room: RoomModel) {
        database.getReference(FirebaseDatabase.ROOM).child(room.roomCode).setValue(room)
    }

    fun joinRoom(roomCode: String, player: Player) {
        database.getReference(FirebaseDatabase.ROOM).child(roomCode).child(FirebaseDatabase.PLAYERS).child(player.id).setValue(player)
    }

    fun createRoomListener(request: ListenerRequest): ValueEventListener {
        return database.getReference(FirebaseDatabase.ROOM).child(request.roomCode).addValueEventListener(request.listener)
    }

    fun getRoom(roomCode: String): Task<DataSnapshot> {
        return database.getReference(FirebaseDatabase.ROOM).child(roomCode).get()
    }

    fun startGame(roomCode: String, rules: Rules) {
        database.getReference(FirebaseDatabase.ROOM).child(roomCode).child(FirebaseDatabase.RULES).setValue(rules)
    }
}