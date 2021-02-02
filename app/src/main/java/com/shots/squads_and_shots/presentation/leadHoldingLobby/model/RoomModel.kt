package com.shots.squads_and_shots.presentation.leadHoldingLobby.model

import android.os.Parcelable
import com.shots.squads_and_shots.presentation.models.GeneralRule
import com.shots.squads_and_shots.presentation.models.NominatedRule
import com.shots.squads_and_shots.presentation.models.SecretTasks
import kotlinx.parcelize.Parcelize

class RoomModel {
    var players: HashMap<String, Player> = HashMap()
    var generalRules: List<GeneralRule>? = null
    var nominatedRules: List<NominatedRule>? = null
    var secretTasks: List<SecretTasks>? = null
    var roomCode: String = ""
}

@Parcelize
class Player: Parcelable {
    var id: String = ""
    var name: String = ""
}
