package com.shots.squads_and_shots.presentation.mainGameRoom

import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import com.shots.squads_and_shots.databinding.RulesCardBinding

class RulesAdapter(private val onClickListeners: RulesViewHolder.OnClickListener) : RecyclerView.Adapter<RulesViewHolder>() {

    private val items = mutableListOf<RuleViewHolderItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RulesViewHolder {
        val binding = RulesCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return RulesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RulesViewHolder, position: Int) {
        holder.bind(items[position], onClickListeners)
    }

    fun updateItems(newItems: List<RuleViewHolderItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

}