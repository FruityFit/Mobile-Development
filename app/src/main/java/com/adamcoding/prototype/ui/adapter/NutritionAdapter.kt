package com.adamcoding.prototype.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adamcoding.prototype.databinding.RowNutritionBinding
import com.adamcoding.prototype.ui.fruit_result.model.FruitNutrition

class NutritionAdapter : ListAdapter<FruitNutrition, NutritionAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutritionAdapter.ViewHolder {
        val binding = RowNutritionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NutritionAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: RowNutritionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(nutrition: FruitNutrition) {
            binding.apply {
                tvName.text = nutrition.name
                tvValue.text = nutrition.value
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FruitNutrition>() {
            override fun areItemsTheSame(
                oldItem: FruitNutrition,
                newItem: FruitNutrition,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: FruitNutrition,
                newItem: FruitNutrition,
            ): Boolean {
                return oldItem.value == newItem.value
            }
        }
    }
}