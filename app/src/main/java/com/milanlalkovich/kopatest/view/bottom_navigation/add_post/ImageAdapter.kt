package com.milanlalkovich.kopatest.view.bottom_navigation.add_post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.milanlalkovich.kopatest.core.recycler_view_adapter.BindingHolder
import com.milanlalkovich.kopatest.databinding.SecondImageBinding
import com.milanlalkovich.kopatest.databinding.SetImageBinding
import com.milanlalkovich.kopatest.domain.entity.ImageEntity


class ImageAdapter(private val onClick: (ImageEntity) -> Unit) :
    RecyclerView.Adapter<BindingHolder<*>>() {

    private var onAddImageListener: (() -> Unit)? = null

    fun setOnAddImageListener(listener: () -> Unit) {
        onAddImageListener = listener
    }

    var list: List<ImageEntity> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<*> =
        when (viewType) {
            ViewType.BUTTON -> {
                BindingHolder(
                    SetImageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                )
            }
            else -> BindingHolder(
                SecondImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
        }

    override fun onBindViewHolder(holder: BindingHolder<*>, position: Int) {
        if (getItemViewType(holder.adapterPosition) == ViewType.BUTTON) {
            holder.binding.root.setOnClickListener {
                onAddImageListener?.invoke()
            }


        } else {
            holder.binding as SecondImageBinding
            holder.binding.root.setOnClickListener(null)
            Glide.with(holder.binding.root.context)
                .load((list[holder.adapterPosition] as ImageEntity.Image).imageUri)
                .into(holder.binding.image)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is ImageEntity.SelectImage -> ViewType.BUTTON
            is ImageEntity.Image -> ViewType.SELECTED_IMAGE
        }
    }

    override fun getItemCount(): Int = list.size
}