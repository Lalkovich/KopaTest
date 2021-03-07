package com.milanlalkovich.kopatest.view.bottom_navigation.main_screen

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.milanlalkovich.kopatest.R
import com.milanlalkovich.kopatest.core.extensions.nonNullObserve
import com.milanlalkovich.kopatest.core.fragment.BaseVMFragment
import com.milanlalkovich.kopatest.databinding.FragmentMainScreenBinding
import com.milanlalkovich.kopatest.view.boots.BootsAdapter
import com.milanlalkovich.kopatest.view.boots.BootsViewModel
import kotlin.reflect.KClass

/**
 *  Created by Android Studio on 03.03.2021 16:06
 *  Developer: Dima Iakubenko
 */

class MainScreenFragment : BaseVMFragment<BootsViewModel, FragmentMainScreenBinding>() {
    override val viewModelClass: KClass<BootsViewModel>
        get() = BootsViewModel::class
    override val layoutId: Int
        get() = R.layout.fragment_main_screen

    private val adapter: BootsAdapter = BootsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listView.adapter = adapter
        adapter.setOnClickListener {
            (parentFragment as NavHostFragment)
                .parentFragment
                ?.findNavController()
                ?.navigate(R.id.action_menuFragment_to_aboutFragment, bundleOf("boots_id" to it))
        }

        viewModel.boots.nonNullObserve(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getBoots()
        }

    }
}