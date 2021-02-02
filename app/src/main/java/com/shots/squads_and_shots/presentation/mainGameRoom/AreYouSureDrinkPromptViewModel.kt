package com.shots.squads_and_shots.presentation.mainGameRoom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shots.squads_and_shots.domain.SubmitDrinkRequestUseCase
import com.shots.squads_and_shots.network.models.DrinkOccasion
import com.shots.squads_and_shots.network.models.DrinkRequest
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.Player

class AreYouSureDrinkPromptViewModel(private val submitDrinkRequestUseCase: SubmitDrinkRequestUseCase) :
    ViewModel() {

    private val _drinkRequestResponse = MutableLiveData<Boolean>()
    val drinkRequestResponse: LiveData<Boolean> get() = _drinkRequestResponse

    fun submitDrinkRequest(
        players: List<Player>,
        ruleTitle: String,
        ruleFunnyString: String,
        roomCode: String
    ) {
        submitDrinkRequestUseCase.invoke(
            viewModelScope,
            createDrinkRequest(players, ruleTitle, ruleFunnyString, roomCode)
        ) { result ->
            result.result(
                onSuccess = {
                    _drinkRequestResponse.value = it
                },
                onFailure = {
                    _drinkRequestResponse.value = false
                }
            )
        }
    }

    private fun createDrinkRequest(
        players: List<Player>,
        ruleTitle: String,
        ruleFunnyString: String,
        roomCode: String
    ): DrinkRequest {

        val occasion = DrinkOccasion()
        occasion.players = players
        occasion.ruleTitle = ruleTitle
        occasion.ruleFunnyString = ruleFunnyString

        val drinkRequest = DrinkRequest()
        drinkRequest.roomCode = roomCode
        drinkRequest.drink = occasion

        return drinkRequest
    }

}