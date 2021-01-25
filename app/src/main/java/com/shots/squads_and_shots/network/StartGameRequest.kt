package com.shots.squads_and_shots.network

import com.shots.squads_and_shots.presentation.models.Rules

data class StartGameRequest(
    val roomCode: String,
    val rules: Rules
)

