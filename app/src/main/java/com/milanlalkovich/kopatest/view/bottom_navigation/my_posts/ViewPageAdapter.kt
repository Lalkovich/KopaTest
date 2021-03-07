package com.milanlalkovich.kopatest.view.bottom_navigation.my_posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.milanlalkovich.kopatest.R

/**
 *  Created by Android Studio on 07.03.2021 21:38
 *  Developer: Dima Iakubenko
 */

class ViewPageAdapter : RecyclerView.Adapter<PagerVH>() {

    private val colors = intArrayOf(
        android.R.color.darker_gray,
        android.R.color.darker_gray

    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(LayoutInflater.from(parent.context).inflate(R.layout.fragment_active, parent, false))

    override fun getItemCount(): Int = colors.size

    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.itemView.run {

    }
}

class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView)