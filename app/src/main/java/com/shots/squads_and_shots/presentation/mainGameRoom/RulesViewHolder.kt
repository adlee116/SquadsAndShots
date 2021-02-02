package com.shots.squads_and_shots.presentation.mainGameRoom

import androidx.recyclerview.widget.RecyclerView
import com.shots.squads_and_shots.databinding.RulesCardBinding

class RulesViewHolder(private val binding: RulesCardBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(rule: RuleViewHolderItem, clickListener: OnClickListener) {
        binding.ruleTitle.text = rule.title
        binding.ruleItem.setOnClickListener { clickListener.ruleCardClicked(rule) }
        binding.shotGlassImage.setOnClickListener { clickListener.drinkClicked(rule) }
    }

    interface OnClickListener {
        fun drinkClicked(rule: RuleViewHolderItem)
        fun ruleCardClicked(rule: RuleViewHolderItem)
    }
}