package com.shots.squads_and_shots.presentation.mainGameRoom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.shots.squads_and_shots.databinding.AreYouSureDrinkPromptFragmentBinding
import com.shots.squads_and_shots.network.models.DrinkOccasion
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.Player
import org.koin.androidx.viewmodel.ext.android.viewModel

class AreYouSureDrinkPromptFragment: DialogFragment() {

    private var _binding: AreYouSureDrinkPromptFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AreYouSureDrinkPromptViewModel by viewModel()

    lateinit var roomCode: String
    lateinit var playersToDrink: ArrayList<Player>
    lateinit var ruleTitle: String
    lateinit var ruleFunnyString: String
    lateinit var playersToDrinkAdapter: PlayersToDrinkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = AreYouSureDrinkPromptFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            it.getParcelableArrayList<Player>(PLAYERS)?.let { players ->
                   playersToDrink = players
            }
            it.getString(ROOM_CODE)?.let { code ->
                roomCode = code
            }
            it.getString(RULE_TITLE)?.let { drinkRuleTitle ->
                ruleTitle = drinkRuleTitle
            }
            it.getString(RULE_FUNNY_STRING)?.let { drinkFunny ->
                ruleFunnyString = drinkFunny
            }
        }

        setAdapter()
        setObservers()
        viewModel.observeDrinkHistoryList(roomCode)
        setOnClickListeners()
    }

    private fun setAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.peopleToDrink.layoutManager = layoutManager

        playersToDrinkAdapter = PlayersToDrinkAdapter()
        binding.peopleToDrink.adapter = playersToDrinkAdapter
    }

    private fun setObservers() {
        viewModel.drinkRequestResponse.observe(viewLifecycleOwner, Observer {
            if (!it) {
                Toast.makeText(requireContext(), "Failed to submit drink, please try again", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.drinkHistory.observe(viewLifecycleOwner, Observer {
            viewModel.checkIfINeedToDrink(it)
        })

        viewModel.drinkOccasions.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()) {
                showTimeToDrinkDialog(it)
            }
        })
    }

    private fun setOnClickListeners() {
        binding.makeThemDrinkButton.setOnClickListener {
            viewModel.submitDrinkRequest(playersToDrink, ruleTitle, ruleFunnyString, roomCode)
        }
    }

    private fun showTimeToDrinkDialog(list: List<DrinkOccasion>) {

    }

    companion object {
        const val PLAYERS = "players"
        const val ROOM_CODE = "roomCode"
        const val RULE_TITLE = "ruleTitle"
        const val RULE_FUNNY_STRING = "ruleFunnyString"

        fun newInstance(players: ArrayList<Player>, roomCode: String, ruleTitle: String, ruleFunnyString: String): AreYouSureDrinkPromptFragment =
            AreYouSureDrinkPromptFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(PLAYERS, players)
                    putString(ROOM_CODE, roomCode)
                    putString(RULE_TITLE, ruleTitle)
                    putString(RULE_FUNNY_STRING, ruleFunnyString)
                }
            }
    }
}