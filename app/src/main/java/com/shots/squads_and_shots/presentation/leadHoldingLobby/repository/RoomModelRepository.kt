package com.shots.squads_and_shots.presentation.leadHoldingLobby.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.shots.squads_and_shots.network.DatabaseInterface
import com.shots.squads_and_shots.network.FirebaseDatabase
import com.shots.squads_and_shots.presentation.leadHoldingLobby.liveData.RoomModelLiveData
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.ListenerRequest
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.Player
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.RoomModel

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
}