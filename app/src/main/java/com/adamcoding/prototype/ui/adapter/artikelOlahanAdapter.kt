package com.adamcoding.prototype.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adamcoding.prototype.data.remote.response.ProductResponse
import com.adamcoding.prototype.databinding.RowOlahanBinding
import com.bumptech.glide.Glide

class artikelOlahanAdapter(private val onClick: (ProductResponse) -> Unit) : ListAdapter<ProductResponse, artikelOlahanAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): artikelOlahanAdapter.ViewHolder {
        return ViewHolder(RowOlahanBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: artikelOlahanAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: RowOlahanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(productResponse: ProductResponse) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(productResponse.imageUrl)
                    .into(ivBuah)

                tvName.text = productResponse.name
            }
            itemView.setOnClickListener {
                onClick(productResponse)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductResponse>() {
            override fun areItemsTheSame(
                oldItem: ProductResponse,
                newItem: ProductResponse,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ProductResponse,
                newItem: ProductResponse,
            ): Boolean {
                return oldItem.productId == newItem.productId
            }
        }
    }
}