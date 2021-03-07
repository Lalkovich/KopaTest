package com.milanlalkovich.kopatest.di

import com.milanlalkovich.kopatest.data.repository.BootsRepositoryImpl
import com.milanlalkovich.kopatest.data.repository.UserRepositoryImpl
import com.milanlalkovich.kopatest.domain.repository.BootsRepository
import com.milanlalkovich.kopatest.domain.repository.UserRepository
import org.koin.dsl.module

val repositoriesModule = module {
    single<BootsRepository> { BootsRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
}