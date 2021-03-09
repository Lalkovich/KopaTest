package com.milanlalkovich.kopatest.view.about_fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.milanlalkovich.kopatest.R
import com.milanlalkovich.kopatest.core.extensions.nonNullObserve
import com.milanlalkovich.kopatest.core.fragment.BaseVMFragment
import com.milanlalkovich.kopatest.databinding.FragmentAboutBinding
import timber.log.Timber
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getBootsById(arguments?.getString("boots_id") ?: "")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserves()
        binding.backAbout.setOnClickListener {
            findNavController().navigate(R.id.action_aboutFragment_to_menuFragment)
        }
    }

    private fun initObserves() {
        viewModel.boots.nonNullObserve(viewLifecycleOwner) {
            binding.title.text = it.title
            binding.bootsLength.text = it.bootsLength.toString()
            binding.materialTitle.text = it.material
            binding.price.text = it.price.toString()
            binding.width.text = it.width.toString()
            binding.description.text = it.description

            Glide.with(this)
                .load(it.imageUrl)
                .into(binding.ivAbout)
                Timber.d(it.toString())
            binding.item = it
        }
    }
}