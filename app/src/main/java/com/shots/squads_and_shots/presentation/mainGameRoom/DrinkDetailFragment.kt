package com.shots.squads_and_shots.presentation.mainGameRoom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.shots.squads_and_shots.databinding.DrinkDetailFragmentBinding

class DrinkDetailFragment : DialogFragment() {

    private var _binding: DrinkDetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DrinkDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            binding.drinkTitle.text = it.getString(TITLE)
            binding.ruleDescription.text = it.getString(DESCRIPTION)
        }
    }

    companion object {
        const val TITLE = "title"
        const val DESCRIPTION = "description"

        fun newInstance(
            ruleTitle: String,
            ruleDescription: String
        ): DrinkDetailFragment =
            DrinkDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(TITLE, ruleTitle)
                    putString(DESCRIPTION, ruleDescription)
                }
            }
    }
}