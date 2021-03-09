package com.milanlalkovich.kopatest.view.bottom_navigation.my_posts

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 *  Created by Android Studio on 09.03.2021 23:18
 *  Developer: Dima Iakubenko
 */

class ViewStateAdapter(fragment:Fragment) : FragmentStateAdapter(fragment) {


    private val fragments = listOf<Fragment>(
        ActiveFragment(),
        ArchivedFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}