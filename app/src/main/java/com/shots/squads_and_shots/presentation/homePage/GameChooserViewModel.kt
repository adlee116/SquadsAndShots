package com.shots.squads_and_shots.presentation.homePage

import androidx.lifecycle.ViewModel
//import com.shots.squads_and_shots.logging.FirebaseAnalytics

class GameChooserViewModel: ViewModel() {

    fun createGame(gameType: String) {
        storeGameCodeLocally()
        storePrivateCodeLocally()
        storeGameType()
    }

    private fun storeGameCodeLocally() {

    }

    private fun storePrivateCodeLocally() {

    }

    private fun storeGameType() {

    }
}