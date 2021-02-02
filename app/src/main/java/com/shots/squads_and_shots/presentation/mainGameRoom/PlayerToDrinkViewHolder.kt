package com.shots.squads_and_shots.presentation.mainGameRoom

import androidx.recyclerview.widget.RecyclerView
import com.shots.squads_and_shots.databinding.DrinkPlayersCardBinding
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.Player

class PlayerToDrinkViewHolder(private val binding: DrinkPlayersCardBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(player: Player) {
        binding.playerName.text = player.name
    }
}