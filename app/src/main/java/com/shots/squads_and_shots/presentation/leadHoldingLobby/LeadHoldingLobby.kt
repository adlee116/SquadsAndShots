package com.shots.squads_and_shots.presentation.leadHoldingLobby

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.shots.squads_and_shots.databinding.LeadHoldingLobbyBinding
import com.shots.squads_and_shots.presentation.homePage.GameChooserDialog
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.Player
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.RoomModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.HashMap

class LeadHoldingLobby : AppCompatActivity() {

    lateinit var adapter: PlayerListAdapter
    lateinit var binding: LeadHoldingLobbyBinding
    lateinit var uniqueUserCode: String
    lateinit var gameCode: String

    private val viewModel: LeadHoldingLobbyViewModel by viewModel()
    private var userName: String? = null
    private var leader: Boolean = false
    lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LeadHoldingLobbyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setLocalInfo()
        setAdapter()
        setObservers()
        setClickListeners()

        userName?.let {
            player = createPersonalPlayer(it, uniqueUserCode)
        }

        adapter.addPlayer(player.name)
        if (leader) {
            createRoom(gameCode, player)
            binding.lobbyCode.text = gameCode
        } else {
            viewModel.createValueEventListener(gameCode)
        }
    }

    private fun setLocalInfo() {
        userName = intent.getStringExtra(GameChooserDialog.USERNAME)
        leader = intent.getBooleanExtra(GameChooserDialog.LEADER, false)
        uniqueUserCode = generateUniqueUserCode()
        gameCode = if(leader) {
            generateGameCode()
        } else {
            intent.getStringExtra(GameChooserDialog.GAME_CODE)!!
        }
    }

    private fun setObservers() {
        viewModel.roomModelCreatedResult.observe(this, Observer {
            viewModel.createValueEventListener(gameCode)
        })
        viewModel.roomModelLiveData.observe(this, Observer {
            if (!leader) {
                binding.lobbyCode.text = it.roomCode
                viewModel.joinRoom(it.roomCode, player)
            }
            updatePlayers(it.players)
        })
        viewModel.roomModelJoinedResult.observe(this, Observer {

        })
    }

    private fun setAdapter() {
        adapter = PlayerListAdapter()
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.playerNameList.layoutManager = layoutManager
        binding.playerNameList.adapter = adapter
    }

    private fun setClickListeners() {
        binding.startGameButton.setOnClickListener {
            Toast.makeText(this, "Starting game", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updatePlayers(players: HashMap<String, Player>) {
        val existingPlayers = adapter.getCurrentPlayerNames()
        players.forEach { map ->
            val currentPlayer = map.value
            if (!existingPlayers.contains(currentPlayer.name)) {
                adapter.addPlayer(currentPlayer.name)
            }
        }
    }

    private fun createRoom(roomCode: String, leadPlayer: Player) {
        val roomModel = RoomModel()
        roomModel.players[leadPlayer.id] = leadPlayer
        roomModel.roomCode = roomCode

        viewModel.createRoomOnFirebase(roomModel)
    }

    private fun generateGameCode(): String {
        return UUID.randomUUID().toString().substring(0, 5)
    }

    private fun generateUniqueUserCode(): String {
        return UUID.randomUUID().toString()
    }

    private fun createPersonalPlayer(userName: String, uniqueUserCode: String): Player {
        val player = Player()
        player.id = uniqueUserCode
        player.name = userName
        return player
    }
}