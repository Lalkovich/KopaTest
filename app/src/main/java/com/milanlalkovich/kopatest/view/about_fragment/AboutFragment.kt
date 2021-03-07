package com.milanlalkovich.kopatest.view.about_fragment

import android.os.Bundle
import android.view.View
import com.milanlalkovich.kopatest.R
import com.milanlalkovich.kopatest.core.extensions.nonNullObserve
import com.milanlalkovich.kopatest.core.fragment.BaseVMFragment
import com.milanlalkovich.kopatest.databinding.FragmentAboutBinding
import kotlin.reflect.KClass

/**
 *  Created by Android Studio on 07.03.2021 13:43
 *  Developer: Dima Iakubenko
 */

class AboutFragment : BaseVMFragment<AboutViewModel, FragmentAboutBinding>() {
    override val viewModelClass: KClass<AboutViewModel>
        get() = AboutViewModel::class
    override val layoutId: Int
        get() = R.layout.fragment_about

    val adapter = AboutAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getBootsById(arguments?.getString("boots_id") ?: "")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserves()
    }

    private fun initObserves() {
        viewModel.boots.nonNullObserve(viewLifecycleOwner) {
            binding.item = it
        }
    }
}