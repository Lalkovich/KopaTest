package com.milanlalkovich.kopatest.view.about_fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.milanlalkovich.kopatest.R
import com.milanlalkovich.kopatest.core.fragment.BaseBindingFragment
import com.milanlalkovich.kopatest.databinding.FragmentImageBinding

/**
 *  Created by Android Studio on 15.03.2021 21:04
 *  Developer: Dima Iakubenko
 */

class ImageFragment : BaseBindingFragment<FragmentImageBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_image

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString(BUNDLE_KEY_IMAGE)?.let {
            Glide.with(requireContext())
                .load(it)
                .into(binding.image)
        }
    }

    companion object {
        private const val BUNDLE_KEY_IMAGE = "imageUrl"
        fun newInstance(imageUrl: String): ImageFragment {
            return ImageFragment().apply {
                arguments = bundleOf(BUNDLE_KEY_IMAGE to imageUrl)
            }
        }
    }
}