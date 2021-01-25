package com.shots.squads_and_shots.presentation.leadHoldingLobby

import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.QuerySnapshot
import com.shots.squads_and_shots.domain.*
import com.shots.squads_and_shots.network.StartGameRequest
import com.shots.squads_and_shots.network.models.RuleListeners
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.JoinModel
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.ListenerRequest
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.Player
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.RoomModel
import com.shots.squads_and_shots.presentation.models.Rules

class LeadHoldingLobbyViewModel(
    private val createRoomUseCase: CreateRoomUseCase,
    private val joinRoomUseCase: JoinRoomUseCase,
    private val addRoomListener: AddRoomListener,
    private val getGeneralRulesUseCase: GetGeneralRulesUseCase,
    private val startGameUseCase: StartGameUseCase
) : ViewModel() {

    var roomModelCreatedResult: MutableLiveData<Boolean> = MutableLiveData(false)
    var roomModelJoinedResult: MutableLiveData<Boolean> = MutableLiveData(false)

    private val _roomModelLiveData = MutableLiveData<RoomModel>()
    val roomModelLiveData: LiveData<RoomModel> get() = _roomModelLiveData

    private val _generalRules = MutableLiveData<Rules>()
    val generalRules : LiveData<Rules> get() = _generalRules

    fun joinRoom(roomCode: String, player: Player) {
        val joinModel = JoinModel(roomCode, player)
        joinRoomUseCase.invoke(viewModelScope, joinModel) { result ->
            result.result(
                onSuccess = {
                    roomModelJoinedResult.value = it
                },
                onFailure = {
                    roomModelJoinedResult.value = false
                }
            )
        }
    }

    fun createRoomOnFirebase(room: RoomModel) {
        createRoomUseCase.invoke(viewModelScope, room) { result ->
            result.result(
                onSuccess = {
                    roomModelCreatedResult.value = it
                },
                onFailure = {
                    roomModelCreatedResult.value = false
                }
            )

        }
    }

    fun createValueEventListener(roomCode: String) {
        val listenerRequest = createRoomListenerRequest(roomCode)
        addRoomListener.invoke(viewModelScope, listenerRequest) { result ->
            result.result(
                onSuccess = {},
                onFailure = {}
            )
        }
    }

    fun getGeneralRules() {
        val rulesListeners = createRuleListeners()
        getGeneralRulesUseCase.invoke(viewModelScope, rulesListeners) {}
    }

    private fun createRuleListeners(): RuleListeners {
        val success = OnSuccessListener<QuerySnapshot> {
            _generalRules.value = it.documents.get(0).toObject(Rules::class.java)
        }
        val failure = OnFailureListener {
            Log.d("Creare rule listeners","Failed, unsure what to do here just yet")
        }
        return RuleListeners(success, failure)

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

    fun startGame(roomModel: RoomModel) {
        startGameUseCase.invoke(viewModelScope, roomModel)
    }

}