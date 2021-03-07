package com.milanlalkovich.kopatest.view.bottom_navigation.my_posts

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.milanlalkovich.kopatest.R
import com.milanlalkovich.kopatest.core.extensions.nonNullObserve
import com.milanlalkovich.kopatest.core.fragment.BaseVMFragment
import com.milanlalkovich.kopatest.databinding.FragmentActiveBinding
import com.milanlalkovich.kopatest.view.boots.BootsAdapter
import kotlin.reflect.KClass

/**
 *  Created by Android Studio on 07.03.2021 21:06
 *  Developer: Dima Iakubenko
 */

class ActiveFragment: BaseVMFragment<MyPostsViewModel,FragmentActiveBinding>() {
    override val viewModelClass: KClass<MyPostsViewModel>
        get() = MyPostsViewModel::class
    override val layoutId: Int
        get() = R.layout.fragment_active

    private val adapter: BootsAdapter = BootsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listActive.adapter = adapter

        adapter.setOnClickListener {
            (parentFragment as NavHostFragment)
                .parentFragment
                ?.findNavController()
                ?.navigate(R.id.action_menuFragment_to_aboutFragment, bundleOf("boots_id" to it))
        }

        viewModel.active.nonNullObserve(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getActiveBoots()
        }
    }
}