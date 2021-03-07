package com.milanlalkovich.kopatest.domain.repository

import com.milanlalkovich.kopatest.domain.model.response.UserModel
import io.reactivex.Completable
import io.reactivex.Single

/**
 *  Created by Android Studio on 26.02.2021 12:34
 *  Developer: Dima Iakubenko
 */

interface UserRepository {
    fun createUsers(newUser: UserModel): Completable
    fun getUserData(): Single<UserModel>
    fun isUserExist(): Single<Boolean>
}