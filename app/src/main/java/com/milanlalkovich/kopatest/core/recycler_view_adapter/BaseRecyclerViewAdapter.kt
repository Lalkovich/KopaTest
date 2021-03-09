package com.milanlalkovich.kopatest.core.recycler_view_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter


abstract class BaseRecyclerViewAdapter<T, Binding : ViewDataBinding>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BindingHolder<Binding>>(diffCallback) {

    protected abstract val layoutId: Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<Binding> =
        BindingHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
        )

}