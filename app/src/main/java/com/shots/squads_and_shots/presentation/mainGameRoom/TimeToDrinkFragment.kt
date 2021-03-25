package com.shots.squads_and_shots.presentation.mainGameRoom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.shots.squads_and_shots.databinding.TimeToDrinkDialogBinding

class TimeToDrinkFragment: DialogFragment() {

    lateinit var adapter: ReasonsToDrinkAdapter

    private var _binding: TimeToDrinkDialogBinding? = null
    private val binding get() = _binding!!

    var drinkCount: Int = 0
    var drinkItems: ArrayList<String> = java.util.ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TimeToDrinkDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            drinkCount = it.getInt(DRINKS_REQUIRED_COUNT)
            drinkItems = it.getStringArrayList(DRINKS_REQUIRED_STRINGS) as ArrayList<String>
        }
        setAdapter()
        setData(drinkCount, drinkItems)
    }

    private fun setAdapter() {
        adapter = ReasonsToDrinkAdapter()
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.drinkReasonItems.layoutManager = layoutManager
        binding.drinkReasonItems.adapter = adapter
    }

    private fun setData(drinkCount: Int, drinkItems: List<String>) {
        binding.drinkCounter.text = drinkCount.toString()
        adapter.updateItems(drinkItems)
    }

    companion object {
        const val DRINKS_REQUIRED_COUNT = "drinksRequiredCount"
        const val DRINKS_REQUIRED_STRINGS = "drinksRequiredStrings"

        fun newInstance(drinkCount: Int, drinkStrings: ArrayList<String>): TimeToDrinkFragment =
            TimeToDrinkFragment().apply {
                arguments = Bundle().apply {
                    putInt(DRINKS_REQUIRED_COUNT, drinkCount)
                    putStringArrayList(DRINKS_REQUIRED_STRINGS, drinkStrings)
                }
            }
    }
}