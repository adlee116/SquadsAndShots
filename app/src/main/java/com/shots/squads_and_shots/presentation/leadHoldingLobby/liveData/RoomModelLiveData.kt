package com.shots.squads_and_shots.presentation.leadHoldingLobby.liveData

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.RoomModel

class RoomModelLiveData(databaseReference: DatabaseReference) : LiveData<RoomModel>(), ValueEventListener {

    var room: MutableLiveData<RoomModel> = MutableLiveData()

    var snapshot = databaseReference.get()

    override fun onDataChange(snapshot: DataSnapshot) {
        room.value = snapshot.getValue(RoomModel::class.java)
    }

    override fun onCancelled(error: DatabaseError) {
        TODO("Not yet implemented")
    }
}