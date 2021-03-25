package com.shots.squads_and_shots.presentation.mainGameRoom

import androidx.recyclerview.widget.RecyclerView
import com.shots.squads_and_shots.databinding.DrinkReasonLayoutItemBinding

class ReasonToDrinkViewHolder(private val binding: DrinkReasonLayoutItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(reason: String) {
        binding.drinkItemText.text = reason
    }
}