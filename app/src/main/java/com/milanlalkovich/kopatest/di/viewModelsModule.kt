package com.milanlalkovich.kopatest.di

import com.milanlalkovich.kopatest.view.about_fragment.AboutViewModel
import com.milanlalkovich.kopatest.view.auth.AuthViewModel
import com.milanlalkovich.kopatest.view.boots.BootsViewModel
import com.milanlalkovich.kopatest.view.bottom_navigation.add_post.AddPostViewModel
import com.milanlalkovich.kopatest.view.bottom_navigation.my_posts.MyPostsViewModel
import com.milanlalkovich.kopatest.view.data_completion.DataCompletionViewModel
import com.milanlalkovich.kopatest.view.phone_auth.PhoneAuthViewModel
import com.milanlalkovich.kopatest.view.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *  Created by Android Studio on 18.02.2021 22:06
 *  Developer: Dima Iakubenko
 */

val viewModelsModule = module {
    viewModel { BootsViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { DataCompletionViewModel(get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { PhoneAuthViewModel(get()) }
    viewModel { AboutViewModel(get()) }
    viewModel { AddPostViewModel(get()) }
    viewModel { MyPostsViewModel(get()) }
}