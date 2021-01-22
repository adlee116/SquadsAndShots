package com.shots.squads_and_shots.presentation.leadHoldingLobby

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shots.squads_and_shots.databinding.PlayerNameCardBinding

class PlayerListAdapter : RecyclerView.Adapter<PlayerListViewHolder>() {

    private val items = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerListViewHolder {
        val binding = PlayerNameCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PlayerListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun getCurrentPlayerNames(): MutableList<String> {
        return items
    }

    fun updateItems(newItems: ArrayList<String>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun addPlayer(name: String) {
        //TODO will add animation
        items.add(name)
        notifyDataSetChanged()
    }

    fun removePlayer(name: String) {
        items.remove(name)
        notifyDataSetChanged()
    }
}