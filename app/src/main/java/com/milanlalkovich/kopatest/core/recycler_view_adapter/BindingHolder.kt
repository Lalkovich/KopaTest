package com.milanlalkovich.kopatest.core.recycler_view_adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BindingHolder<B : ViewDataBinding>(val binding: B) : RecyclerView.ViewHolder(binding.root)