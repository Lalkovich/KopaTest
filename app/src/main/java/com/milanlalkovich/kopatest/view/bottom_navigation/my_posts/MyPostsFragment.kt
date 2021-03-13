package com.milanlalkovich.kopatest.view.bottom_navigation.my_posts

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.milanlalkovich.kopatest.R
import com.milanlalkovich.kopatest.core.fragment.BaseVMFragment
import com.milanlalkovich.kopatest.databinding.FragmentMyPostsBinding
import kotlin.reflect.KClass

/**
 *  Created by Android Studio on 03.03.2021 16:06
 *  Developer: Dima Iakubenko
 */

class MyPostsFragment : BaseVMFragment<MyPostsViewModel, FragmentMyPostsBinding>() {
    override val viewModelClass: KClass<MyPostsViewModel>
        get() = MyPostsViewModel::class
    override val layoutId: Int
        get() = R.layout.fragment_my_posts

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ViewStateAdapter(this)

        binding.viewPagerFragment.adapter = adapter

        binding.tabLayout1.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPagerFragment.currentItem = tab?.position ?: 0
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        binding.viewPagerFragment.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout1.selectTab(binding.tabLayout1.getTabAt(position))
            }
        })
    }

    fun navigateToDetails(bundle: Bundle) {
        (parentFragment as NavHostFragment)
            .parentFragment
            ?.findNavController()
            ?.navigate(R.id.action_menuFragment_to_aboutFragment, bundle)
    }

}