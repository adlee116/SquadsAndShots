package com.shots.squads_and_shots.presentation.models

import android.os.Parcelable
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.Player
import kotlinx.parcelize.Parcelize

@Parcelize
open class Rule: Parcelable {
    var title: String = ""
    var description: String = ""
    var image: String = ""
    var players: ArrayList<Player> = java.util.ArrayList()
    var ruleFunnyString = ""
}