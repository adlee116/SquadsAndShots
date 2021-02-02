package com.shots.squads_and_shots.presentation.mainGameRoom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shots.squads_and_shots.databinding.DrinkPlayersCardBinding
import com.shots.squads_and_shots.presentation.leadHoldingLobby.model.Player

class PlayersToDrinkAdapter: RecyclerView.Adapter<PlayerToDrinkViewHolder>() {

    private val items = mutableListOf<Player>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerToDrinkViewHolder {
        val binding = DrinkPlayersCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerToDrinkViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PlayerToDrinkViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateItems(newItems: List<Player>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

}