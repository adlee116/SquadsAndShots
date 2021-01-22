package com.shots.squads_and_shots.presentation.homePage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.shots.squads_and_shots.databinding.GameCodeInputDialogBinding
import com.shots.squads_and_shots.presentation.leadHoldingLobby.LeadHoldingLobby
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameCodeInputDialog : DialogFragment() {

    private var _binding: GameCodeInputDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val viewModel: GameCodeInputViewModel by viewModel()
    private var userName: String? = null
    lateinit var roomCode: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GameCodeInputDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userName = arguments?.getString(GameChooserDialog.USERNAME)
        userName?.let {
            setClickListeners()
            setObservers()
        } ?: run {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun setClickListeners() {
        binding.joinGameButtonWithCode.setOnClickListener {
            val inputRoomCode = binding.enterGameCodeEditText.text.toString()
            if (validateRoomCode(inputRoomCode)) {
                roomCode = inputRoomCode
                viewModel.checkRoomExists(roomCode)
                binding.joinRoomProgressBar.visibility = View.VISIBLE
            } else {
                Toast.makeText(requireContext(), "Please enter a room code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setObservers() {
        viewModel.roomExists.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    binding.joinRoomProgressBar.visibility = View.INVISIBLE
                    goToPreGameLobby(roomCode)
                }
                else -> {
                    binding.warning.visibility = View.VISIBLE
                    binding.warning.postDelayed(
                        Runnable { binding.warning.visibility = View.GONE },
                        3000
                    )
                }
            }
        })
    }

    private fun validateRoomCode(roomCode: String?): Boolean {
        return !roomCode.isNullOrEmpty()
    }

    private fun goToPreGameLobby(roomCode: String) {
        startActivity(
            Intent(requireContext(), LeadHoldingLobby::class.java)
                .putExtra(GameChooserDialog.USERNAME, userName)
                .putExtra(GameChooserDialog.GAME_CODE, roomCode)
        )
    }

    companion object {
        fun newInstance(userName: String): GameCodeInputDialog =
            GameCodeInputDialog().apply {
                arguments = Bundle().apply {
                    putString(GameChooserDialog.USERNAME, userName)
                }
            }
    }

}