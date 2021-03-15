package com.milanlalkovich.kopatest.view.about_fragment

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.milanlalkovich.kopatest.core.recycler_view_adapter.BindingHolder
import com.milanlalkovich.kopatest.domain.entity.ImageEntity

/**
 *  Created by Android Studio on 15.03.2021 19:01
 *  Developer: Dima Iakubenko
 */

class ViewPagerAdapter(
    private val images: List<String>,
    fragment:Fragment
):FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = images.size



    override fun createFragment(position: Int): Fragment {
        return ImageFragment.newInstance(imageUrl = images[position])
    }


}