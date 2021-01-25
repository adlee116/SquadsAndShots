package com.shots.squads_and_shots.presentation.leadHoldingLobby.model

import com.shots.squads_and_shots.presentation.models.GeneralRule
import com.shots.squads_and_shots.presentation.models.NominatedRule
import com.shots.squads_and_shots.presentation.models.SecretTasks

class RoomModel {
    var players: HashMap<String, Player> = HashMap()
    var generalRules: List<GeneralRule>? = null
    var nominatedRules: List<NominatedRule>? = null
    var secretTasks: List<SecretTasks>? = null
    var roomCode: String = ""
}

class Player {
    var id: String = ""
    var name: String = ""
}
