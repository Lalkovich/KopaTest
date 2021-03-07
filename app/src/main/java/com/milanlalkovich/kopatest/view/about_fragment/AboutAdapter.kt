package com.milanlalkovich.kopatest.view.about_fragment

import com.bumptech.glide.Glide
import com.milanlalkovich.kopatest.R
import com.milanlalkovich.kopatest.core.recycler_view_adapter.BaseRecyclerViewAdapter
import com.milanlalkovich.kopatest.core.recycler_view_adapter.BindingHolder
import com.milanlalkovich.kopatest.databinding.FragmentAboutBinding
import com.milanlalkovich.kopatest.domain.model.response.Boots
import com.milanlalkovich.kopatest.utils.BootsDiffCallback

/**
 *  Created by Android Studio on 07.03.2021 13:44
 *  Developer: Dima Iakubenko
 */

class AboutAdapter(): BaseRecyclerViewAdapter<Boots, FragmentAboutBinding>(BootsDiffCallback()) {
    override val layoutId: Int
        get() = R.layout.fragment_about

    override fun onBindViewHolder(holder: BindingHolder<FragmentAboutBinding>, position: Int) {
        val item = getItem(holder.adapterPosition)
        holder.binding.title.text = item.title
        holder.binding.bootsLength.text = item.bootsLength.toString()
        holder.binding.material.text = item.material
        holder.binding.price.text = item.price.toString()
        holder.binding.width.text = item.width.toString()
        holder.binding.description.text = item.description

        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .into(holder.binding.ivAbout)
    }
}