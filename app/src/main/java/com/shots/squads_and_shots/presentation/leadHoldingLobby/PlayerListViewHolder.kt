package com.shots.squads_and_shots.presentation.leadHoldingLobby

import androidx.recyclerview.widget.RecyclerView
import com.shots.squads_and_shots.databinding.PlayerNameCardBinding

class PlayerListViewHolder(val binding: PlayerNameCardBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(playerName: String) {
        binding.playerNameCard.text = playerName
    }
}