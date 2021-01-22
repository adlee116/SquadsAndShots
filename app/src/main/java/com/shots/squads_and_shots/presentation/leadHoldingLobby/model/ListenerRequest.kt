package com.shots.squads_and_shots.presentation.leadHoldingLobby.model

import com.google.firebase.database.ValueEventListener

class ListenerRequest(
    val listener: ValueEventListener,
    val roomCode: String
)