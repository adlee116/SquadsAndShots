package com.shots.squads_and_shots.presentation.homePage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shots.squads_and_shots.databinding.GameChooserDialogBinding
import com.shots.squads_and_shots.presentation.leadHoldingLobby.LeadHoldingLobby
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameChooserDialog : DialogFragment() {

    private var _binding: GameChooserDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: GameChooserViewModel by viewModel()
    lateinit var userName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GameChooserDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            if (it.containsKey(USERNAME)) {
                userName = it.getString(USERNAME)!!
                setClickListeners()
                setObservers()
            } else {
                usernameFailureToast()
            }
        } ?: usernameFailureToast()
        arguments?.get(USERNAME)
        setImages()
    }

    private fun usernameFailureToast() {
        Toast.makeText(
            requireContext(),
            "Sorry, we lost your username, please go back and try again",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setClickListeners() {
        binding.warzoneImage.setOnClickListener {
            viewModel.createGame(WARZONE)
            goToCreatorsHoldingLobby()
        }
        binding.apexImage.setOnClickListener {
            notYetImplemented(APEX)
//            viewModel.createGame(APEX)
//            goToCreatorsHoldingLobby()
        }
        binding.fortniteImage.setOnClickListener {
            notYetImplemented(FORTNITE)
//            viewModel.createGame(FORTNITE)
//            goToCreatorsHoldingLobby()
        }
    }

    private fun goToCreatorsHoldingLobby() {
        startActivity(
            Intent(requireContext(), LeadHoldingLobby::class.java)
                .putExtra(USERNAME, userName)
                .putExtra(LEADER, true)
        )
    }

    private fun setObservers() {

    }

    private fun setImages() {
        Glide.with(requireContext())
            .load("https://droidjournal.com/wp-content/uploads/2020/04/COD.jpg")
            .centerCrop()
            .fitCenter()
            .into(binding.warzoneImage)

        Glide.with(requireContext())
            .load("https://img.redbull.com/images/c_crop,x_71,y_0,h_1080,w_1620/c_fill,w_1500,h_1000/q_auto,f_auto/redbullcom/2019/02/12/0394f2ac-e96d-4b24-8268-a7346fcbd206/apex-legends")
            .centerCrop()
            .fitCenter()
            .into(binding.apexImage)

        Glide.with(requireContext())
            .load("https://cdn2.unrealengine.com/14br-consoles-1920x1080-wlogo-1920x1080-432974386.jpg")
            .centerCrop()
            .fitCenter()
            .into(binding.fortniteImage)

    }

    private fun notYetImplemented(game: String) {
        Toast.makeText(
            requireContext(),
            game.capitalize() + " is not yet implemented, please choose another game",
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        const val WARZONE = "warzone"
        const val APEX = "apex"
        const val FORTNITE = "fortnite"
        const val USERNAME = "username"
        const val GAME_CODE = "gameCode"
        const val LEADER = "leader"

        fun newInstance(userName: String): GameChooserDialog =
            GameChooserDialog().apply {
                arguments = Bundle().apply {
                    putString(USERNAME, userName)
                }
            }
    }

}