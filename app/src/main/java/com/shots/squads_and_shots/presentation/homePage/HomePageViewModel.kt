package com.shots.squads_and_shots.presentation.homePage

import androidx.lifecycle.ViewModel
import java.util.*

class HomePageViewModel: ViewModel() {

    fun createGame(name: String) {
        val code = generateGameCode()
        val privateCode = generatePrivateCode()
        // Store this code and create a local lobby with this game code
        // Generate your own code
    }

    fun joinGame(name: String, gameCode: String) {
        val privateCode = generatePrivateCode()
        // Ping firebase, using code, to let lead know you are available
        // Store this code, so you receive messages from this code
        // Generate your own code?
        // Go to holding lobby while we wait for the lead to start game
    }

    private fun generateGameCode(): String {
        return UUID.randomUUID().toString().substring(0, 5)
    }

    private fun generatePrivateCode(): UUID {
        return UUID.randomUUID()
    }
}