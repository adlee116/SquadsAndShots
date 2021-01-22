package com.shots.squads_and_shots.presentation.leadHoldingLobby

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.shots.squads_and_shots.domain.AddRoomListener
import com.shots.squads_and_shots.domain.CreateRoomUseCase
import com.shots.squads_and_shots.domain.JoinRoomUseCase
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.JoinModel
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.ListenerRequest
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.Player
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.RoomModel

class LeadHoldingLobbyViewModel(
    private val createRoomUseCase: CreateRoomUseCase,
    private val joinRoomUseCase: JoinRoomUseCase,
    private val addRoomListener: AddRoomListener
) : ViewModel() {

    var roomModelCreatedResult: MutableLiveData<Boolean> = MutableLiveData(false)
    var roomModelJoinedResult: MutableLiveData<Boolean> = MutableLiveData(false)

    private val _roomModelLiveData = MutableLiveData<RoomModel>()
    val roomModelLiveData: LiveData<RoomModel> get() = _roomModelLiveData

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

}