package com.milanlalkovich.kopatest.view.boots

import android.os.Bundle
import android.view.View
import com.milanlalkovich.kopatest.R
import com.milanlalkovich.kopatest.core.extensions.nonNullObserve
import com.milanlalkovich.kopatest.core.fragment.BaseVMFragment
import com.milanlalkovich.kopatest.databinding.FragmentMainScreenBinding
import kotlin.reflect.KClass

/**
 *  Created by Android Studio on 02.03.2021 17:43
 *  Developer: Dima Iakubenko
 */

class BootsFragment : BaseVMFragment<BootsViewModel,FragmentMainScreenBinding>() {
    private val adapter: BootsAdapter = BootsAdapter()

    override val viewModelClass: KClass<BootsViewModel>
        get() = BootsViewModel::class
    override val layoutId: Int
        get() = R.layout.list_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserves()
        binding.listView.adapter = adapter
    }

    private fun initObserves() {
        viewModel.boots.nonNullObserve(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}