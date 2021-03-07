package com.milanlalkovich.kopatest.view.boots

import com.bumptech.glide.Glide
import com.milanlalkovich.kopatest.R
import com.milanlalkovich.kopatest.core.recycler_view_adapter.BaseRecyclerViewAdapter
import com.milanlalkovich.kopatest.core.recycler_view_adapter.BindingHolder
import com.milanlalkovich.kopatest.databinding.ListLayoutBinding
import com.milanlalkovich.kopatest.domain.model.response.Boots
import com.milanlalkovich.kopatest.utils.BootsDiffCallback

/**
 *  Created by Android Studio on 05.03.2021 21:26
 *  Developer: Dima Iakubenko
 */

class BootsAdapter:BaseRecyclerViewAdapter<Boots,ListLayoutBinding>(BootsDiffCallback()) {
    override val layoutId: Int
        get() = R.layout.list_layout

    private var onClickListener: ((String) -> Unit)? = null

    override fun onBindViewHolder(holder: BindingHolder<ListLayoutBinding>, position: Int) {
        val item = getItem(holder.adapterPosition)

        holder.binding.title.text = item.title
        holder.binding.price.text = item.price.toString()
        holder.binding.bootsLength.text = item.bootsLength.toString()
        holder.binding.length.text = item.length.toString()
        holder.binding.width.text = item.width.toString()
        holder.binding.materialTitle.text = item.material
        // Check BootsResponse

        holder.binding.root.setOnClickListener {
            onClickListener?.invoke(item.id)
        }

        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .into(holder.binding.image)
    }

    fun setOnClickListener(listener: (String) -> Unit) {
        onClickListener = listener
    }

}