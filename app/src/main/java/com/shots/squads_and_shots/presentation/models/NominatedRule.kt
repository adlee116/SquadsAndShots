package com.shots.squads_and_shots.presentation.models

import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.Player

data class NominatedRule(
    val title: String,
    val description: String,
    val image: String,
    val nominatedPlayer: Player
)