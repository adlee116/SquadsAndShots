package com.shots.squads_and_shots.presentation.leadHoldingLobby

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.shots.squads_and_shots.databinding.LeadHoldingLobbyBinding
import com.shots.squads_and_shots.presentation.homePage.GameChooserDialog
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.Player
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.RoomModel
import com.shots.squads_and_shots.presentation.mainGameRoom.MainGameRoom
import org.koin.androidx.viewmodel.ext.android.viewModel

class LeadHoldingLobby : AppCompatActivity() {

    private val viewModel: LeadHoldingLobbyViewModel by viewModel()
    private var userName: String? = null
    private var leader: Boolean = false

    lateinit var adapter: PlayerListAdapter
    lateinit var binding: LeadHoldingLobbyBinding
    lateinit var gameCode: String
    lateinit var player: Player
    lateinit var roomModel: RoomModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LeadHoldingLobbyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setAdapter()
        setLocalInfo()
        setObservers()
        setClickListeners()
    }

    private fun setLocalInfo() {
        leader = intent.getBooleanExtra(GameChooserDialog.LEADER, false)
        userName = intent.getStringExtra(GameChooserDialog.USERNAME)
        gameCode = if (leader) {
            viewModel.generateGameCode()
        } else {
            intent.getStringExtra(GameChooserDialog.GAME_CODE)!!
        }

        val uniqueUserId = viewModel.generateUniqueUserCode()
        userName?.let { player = viewModel.createPersonalPlayer(it, uniqueUserId) }
        adapter.addPlayer(player.name)
        if (leader) {
            viewModel.createRoom(gameCode, player)
            binding.lobbyCode.text = gameCode
        } else {
            viewModel.createValueEventListener(gameCode)
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
            roomModel = it
            updatePlayers(it.players)
            roomModel.generalRules?.let { rules ->
                if (rules.isNotEmpty()) {
                    goToMainGameRoom(gameCode)
                }
            }
        })
        viewModel.roomModelJoinedResult.observe(this, Observer {

        })
        viewModel.generalRules.observe(this, Observer {
            val filteredRules = viewModel.filterRules(it)
            val players = viewModel.convertIntoPlayers(roomModel.players)

            val assignedRules = viewModel.assignRules(filteredRules, players)
            roomModel.generalRules = assignedRules.generalRules
            roomModel.secretTasks = assignedRules.secretTasks
            roomModel.nominatedRules = assignedRules.nominatedRules
            viewModel.startGame(roomModel)
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
            startGame()
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

    private fun startGame() {
        viewModel.getGeneralRules()
    }

    private fun goToMainGameRoom(roomCode: String) {
        val intent = Intent(this, MainGameRoom::class.java)
        intent.putExtra(GameChooserDialog.GAME_CODE, roomCode)
        startActivity(intent)
    }

}