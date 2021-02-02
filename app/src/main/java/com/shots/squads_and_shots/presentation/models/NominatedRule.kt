package com.shots.squads_and_shots.presentation.models

import android.os.Parcelable
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.Player
import kotlinx.parcelize.Parcelize

@Parcelize
class NominatedRule: Rule(), Parcelable {
    val nominatedPlayer: Player = Player()
}