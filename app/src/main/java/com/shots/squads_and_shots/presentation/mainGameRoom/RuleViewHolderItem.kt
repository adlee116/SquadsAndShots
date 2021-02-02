package com.shots.squads_and_shots.presentation.mainGameRoom

import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.Player

data class RuleViewHolderItem (
    val title: String,
    val description: String,
    val ruleFunnyString: String,
    val image: String,
    val player: ArrayList<Player>
)