package com.shots.squads_and_shots.presentation.mainGameRoom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.shots.squads_and_shots.core.utils.justValue
import com.shots.squads_and_shots.domain.AddRoomListenerUseCase
import com.shots.squads_and_shots.domain.CreateOrGetUserUniqueId
import com.shots.squads_and_shots.domain.ObserveDrinkHistoryListUseCase
import com.shots.squads_and_shots.network.models.DrinkOccasion
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.ListenerRequest
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.Player
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.RoomModel
import com.shots.squads_and_shots.presentation.models.GeneralRule
import com.shots.squads_and_shots.presentation.models.NominatedRule
import com.shots.squads_and_shots.presentation.models.SecretTasks

class GameRoomViewModel(
    private val addRoomListenerUseCase: AddRoomListenerUseCase,
    private val getUserUniqueId: CreateOrGetUserUniqueId,
    private val observeDrinkHistoryListUseCase: ObserveDrinkHistoryListUseCase,
) : ViewModel() {

    private val _roomModelLiveData = MutableLiveData<RoomModel>()
    val roomModelLiveData: LiveData<RoomModel> get() = _roomModelLiveData

    private val _drinkHistory = MutableLiveData<List<DrinkOccasion>>()
    val drinkHistory: LiveData<List<DrinkOccasion>> get() = _drinkHistory

    private val _drinkOccasions = MutableLiveData<List<DrinkOccasion>>()
    val drinkOccasions: LiveData<List<DrinkOccasion>> get() = _drinkOccasions

    private var nextIndexToCheckForDrink = 0

    fun createValueEventListener(roomCode: String) {
        val listenerRequest = createRoomListenerRequest(roomCode)
        addRoomListenerUseCase.invoke(viewModelScope, listenerRequest) {}
    }

    fun observeDrinkHistoryList(roomCode: String) {
        observeDrinkHistoryListUseCase.invoke(viewModelScope, createDrinkOccasionListenerRequest(roomCode))
    }

    fun createGeneralRuleItems(rules: List<GeneralRule>): MutableList<RuleViewHolderItem> {
        val ruleList = mutableListOf<RuleViewHolderItem>()
        rules.forEach {
            ruleList.add(
                createRuleViewHolderItem(
                    it.title, it.description, it.image, it.players
                )
            )
        }
        return ruleList
    }

    fun createNominatedRuleItems(rules: List<NominatedRule>): MutableList<RuleViewHolderItem> {
        val ruleList = mutableListOf<RuleViewHolderItem>()
        rules.forEach {
            ruleList.add(
                createRuleViewHolderItem(
                    it.title, it.description, it.image, it.players
                )
            )
        }
        return ruleList
    }

    private fun createRoomListenerRequest(roomCode: String): ListenerRequest {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    _roomModelLiveData.value = snapshot.getValue(RoomModel::class.java)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        return ListenerRequest(listener, roomCode)
    }

    // Works on the basis we can only have one secret rule per player
    fun secretTaskAvailable(secretTasks: List<SecretTasks>?, playerId: String): SecretTasks? {
        secretTasks?.let { tasks ->
            tasks.forEach { currentTask ->
                currentTask.players.forEach {
                    if(it.id == playerId) {
                        return currentTask
                    }
                }
            }
        }
        return null
    }

    fun getUserUniqueId(): String {
        return getUserUniqueId.justValue()
    }

    fun createRuleViewHolderItem(
        title: String, description: String, image: String, players: ArrayList<Player>?
    ): RuleViewHolderItem {
        var playerObject: ArrayList<Player> = arrayListOf()
        players?.let {
            playerObject = players
        }
        return RuleViewHolderItem(title, description, "you were a twat", image, playerObject)
    }

    fun checkIfINeedToDrink(list: List<DrinkOccasion>) {
        val myDrinkOccasions = mutableListOf<DrinkOccasion>()
        if(list.size > nextIndexToCheckForDrink) {
            for(i in nextIndexToCheckForDrink until list.size)
                if(doesThisOccasionContainMe(list[i])) {
                    myDrinkOccasions.add(list[i])
                }
            nextIndexToCheckForDrink = list.size
        }
        if(myDrinkOccasions.isNotEmpty()) {
            _drinkOccasions.value = myDrinkOccasions
        }
    }

    private fun doesThisOccasionContainMe(drinkOccasion: DrinkOccasion): Boolean {
        var doIDrink = false
        drinkOccasion.players.forEach {
            if(it.id == getUserUniqueId()) {
                doIDrink = true
            }
        }
        return doIDrink
    }

    fun clearOccasionList() {
        _drinkOccasions.value = emptyList()
    }

    private fun createDrinkOccasionListenerRequest(roomCode: String): ListenerRequest {
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
                    _drinkHistory.value = drinkOccasionList
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        return ListenerRequest(listener, roomCode)
    }
}