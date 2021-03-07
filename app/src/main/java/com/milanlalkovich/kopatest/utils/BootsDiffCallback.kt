package com.milanlalkovich.kopatest.utils

import androidx.recyclerview.widget.DiffUtil
import com.milanlalkovich.kopatest.domain.model.response.Boots

class BootsDiffCallback : DiffUtil.ItemCallback<Boots>() {
    override fun areItemsTheSame(oldItem: Boots, newItem: Boots): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Boots, newItem: Boots): Boolean =
        oldItem == newItem
}