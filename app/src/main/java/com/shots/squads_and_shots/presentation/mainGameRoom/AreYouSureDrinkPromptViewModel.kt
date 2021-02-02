package com.shots.squads_and_shots.presentation.mainGameRoom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.shots.squads_and_shots.core.utils.justValue
import com.shots.squads_and_shots.domain.CreateOrGetUserUniqueId
import com.shots.squads_and_shots.domain.ObserveDrinkHistoryListUseCase
import com.shots.squads_and_shots.domain.SubmitDrinkRequestUseCase
import com.shots.squads_and_shots.network.models.DrinkOccasion
import com.shots.squads_and_shots.network.models.DrinkRequest
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.ListenerRequest
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.Player

class AreYouSureDrinkPromptViewModel(
    private val submitDrinkRequestUseCase: SubmitDrinkRequestUseCase,
    private val observeDrinkHistoryListUseCase: ObserveDrinkHistoryListUseCase,
    private val getUserUniqueId: CreateOrGetUserUniqueId
    ) :
    ViewModel() {

    private val _drinkRequestResponse = MutableLiveData<Boolean>()
    val drinkRequestResponse: LiveData<Boolean> get() = _drinkRequestResponse

    private val _drinkHistory = MutableLiveData<List<DrinkOccasion>>()
    val drinkHistory: LiveData<List<DrinkOccasion>> get() = _drinkHistory

    private val _drinkOccasions = MutableLiveData<List<DrinkOccasion>>()
    val drinkOccasions: LiveData<List<DrinkOccasion>> get() = _drinkOccasions

    private var lastKnownDrinkOccasionChecked = -1

    fun observeDrinkHistoryList(roomCode: String) {
        observeDrinkHistoryListUseCase.invoke(viewModelScope, createRoomListenerRequest(roomCode))
    }

    fun submitDrinkRequest(players: List<Player>, ruleTitle: String, ruleFunnyString: String, roomCode: String) {
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

    private fun createRoomListenerRequest(roomCode: String): ListenerRequest {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val drinkOccasionList = mutableListOf<DrinkOccasion>()

                    val children = snapshot.children
                    children.forEach{
                        val drinkOccasion = it.getValue(DrinkOccasion::class.java)
                        drinkOccasion?.let { drink ->
                            drinkOccasionList.add(drink)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        return ListenerRequest(listener, roomCode)
    }

    private fun getUserId(): String {
        return getUserUniqueId.justValue()
    }

    fun checkIfINeedToDrink(list: List<DrinkOccasion>) {
        val myDrinkOccasions = mutableListOf<DrinkOccasion>()
        if(list.size > lastKnownDrinkOccasionChecked) {
            for (i in lastKnownDrinkOccasionChecked until list.size) {
                if(doesThisOccasionContainMe(list[i])) {
                    myDrinkOccasions.add(list[i])
                }
            }
            lastKnownDrinkOccasionChecked = list.size - 1
        }
        if(myDrinkOccasions.isNotEmpty()) {
            _drinkOccasions.value = myDrinkOccasions
        }
    }

    private fun doesThisOccasionContainMe(drinkOccasion: DrinkOccasion): Boolean {
        var doIDrink = false
        drinkOccasion.players.forEach {
            if(it.id == getUserId()) {
                doIDrink = true
            }
        }
        return doIDrink
    }

    fun clearOccasionList() {
        _drinkOccasions.value = emptyList()
    }

}