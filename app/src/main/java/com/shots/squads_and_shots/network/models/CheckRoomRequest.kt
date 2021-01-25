package com.shots.squads_and_shots.network.models

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot

data class CheckRoomRequest(
    val roomCode: String,
    val onSuccessListener: OnSuccessListener<DataSnapshot>,
    val onFailureListener: OnFailureListener
)