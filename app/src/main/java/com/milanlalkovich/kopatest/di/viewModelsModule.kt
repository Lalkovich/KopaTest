package com.milanlalkovich.kopatest.di

import com.milanlalkovich.kopatest.view.auth.AuthViewModel
import com.milanlalkovich.kopatest.view.data_completion.DataCompletionViewModel
import com.milanlalkovich.kopatest.view.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *  Created by Android Studio on 18.02.2021 22:06
 *  Developer: Dima Iakubenko
 */

val viewModelsModule = module {
    viewModel { ProfileViewModel(get()) }
    viewModel { DataCompletionViewModel(get()) }
    viewModel { AuthViewModel(get()) }
}