package com.milanlalkovich.kopatest.view.bottom_navigation.my_posts

import androidx.fragment.app.Fragment
import com.milanlalkovich.kopatest.R
import com.milanlalkovich.kopatest.core.fragment.BaseVMFragment
import com.milanlalkovich.kopatest.databinding.FragmentMyPostsBinding
import kotlin.reflect.KClass

/**
 *  Created by Android Studio on 03.03.2021 16:06
 *  Developer: Dima Iakubenko
 */

class MyPostsFragment : BaseVMFragment<MyPostsViewModel,FragmentMyPostsBinding>() {
    override val viewModelClass: KClass<MyPostsViewModel>
        get() = MyPostsViewModel::class
    override val layoutId: Int
        get() = R.layout.fragment_my_posts

}