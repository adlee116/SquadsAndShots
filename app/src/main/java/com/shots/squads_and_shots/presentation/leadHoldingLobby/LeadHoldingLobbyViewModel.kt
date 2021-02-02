package com.shots.squads_and_shots.presentation.leadHoldingLobby

import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.QuerySnapshot
import com.shots.squads_and_shots.core.utils.justValue
import com.shots.squads_and_shots.domain.*
import com.shots.squads_and_shots.network.models.RuleListeners
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.JoinModel
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.ListenerRequest
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.Player
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.RoomModel
import com.shots.squads_and_shots.presentation.models.Rules
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.random.Random

class LeadHoldingLobbyViewModel(
    private val createRoomUseCase: CreateRoomUseCase,
    private val joinRoomUseCase: JoinRoomUseCase,
    private val addRoomListenerUseCase: AddRoomListenerUseCase,
    private val getAllRulesUseCase: GetAllRulesUseCase,
    private val startGameUseCase: StartGameUseCase,
    private val createOrGetUserUniqueId: CreateOrGetUserUniqueId
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

    private fun createRoomOnFirebase(room: RoomModel) {
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
        addRoomListenerUseCase.invoke(viewModelScope, listenerRequest) { result ->
            result.result(
                onSuccess = {},
                onFailure = {}
            )
        }
    }

    fun getGeneralRules() {
        val rulesListeners = createRuleListeners()
        getAllRulesUseCase.invoke(viewModelScope, rulesListeners) {}
    }

    private fun createRuleListeners(): RuleListeners {
        val success = OnSuccessListener<QuerySnapshot> {
            _generalRules.value = it.documents[0].toObject(Rules::class.java)
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

    fun createPersonalPlayer(userName: String, uniqueUserCode: String): Player {
        val player = Player()
        player.id = uniqueUserCode
        player.name = userName
        return player
    }

    fun generateGameCode(): String = UUID.randomUUID().toString().substring(0, 5)

    fun generateUniqueUserCode(): String = createOrGetUserUniqueId.justValue()

    fun createRoom(roomCode: String, leadPlayer: Player) {
        val roomModel = RoomModel()
        roomModel.players[leadPlayer.id] = leadPlayer
        roomModel.roomCode = roomCode

        createRoomOnFirebase(roomModel)
    }

    fun assignRules(rules: Rules, players: List<Player>): Rules {
        val availablePlayers = players as MutableList
        rules.generalRules.forEach {
            it.players = players as ArrayList<Player>
        }

        rules.secretTasks.forEach {
            val assignedPlayer = availablePlayers.random()
            availablePlayers.remove(assignedPlayer)
            it.players.add(assignedPlayer)
        }
        return rules
    }

    fun filterRules(rules: Rules): Rules {
        val shuffledGeneralList = rules.generalRules.shuffled()
        val shuffledNominatedList = rules.nominatedRules.shuffled()
        val shuffledSecretList = rules.secretTasks.shuffled()

        if(shuffledGeneralList.size < 7) {
            rules.generalRules = shuffledGeneralList
        } else {
            rules.generalRules = shuffledGeneralList.subList(0, 6)
        }

        if(shuffledNominatedList.size < 3) {
            rules.nominatedRules = shuffledNominatedList
        } else {
            rules.nominatedRules = shuffledNominatedList.subList(0, 2)
        }

        if(shuffledSecretList.size < 3) {
            rules.secretTasks = shuffledSecretList.subList(0, Random.nextInt(shuffledSecretList.size))
        } else {
            rules.secretTasks = shuffledSecretList.subList(0, Random.nextInt(3))
        }
        return rules
    }

    fun convertIntoPlayers(players: HashMap<String, Player>): List<Player>{
        val playerList = mutableListOf<Player>()
        players.forEach {
            playerList.add(it.value)
        }
        return playerList
    }

}