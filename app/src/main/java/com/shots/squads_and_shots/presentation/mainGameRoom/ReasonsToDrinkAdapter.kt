package com.shots.squads_and_shots.presentation.mainGameRoom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shots.squads_and_shots.databinding.DrinkReasonLayoutItemBinding

class ReasonsToDrinkAdapter : RecyclerView.Adapter<ReasonToDrinkViewHolder>() {

    private val items = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReasonToDrinkViewHolder {
        val binding = DrinkReasonLayoutItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ReasonToDrinkViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ReasonToDrinkViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateItems(newItems: List<String>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}

