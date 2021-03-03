package com.milanlalkovich.kopatest.data.source.remote

import com.milanlalkovich.kopatest.domain.model.response.UserModel
import io.reactivex.Completable
import io.reactivex.Single

/**
 *  Created by Android Studio on 22.02.2021 17:02
 *  Developer: Dima Iakubenko
 */

interface FBDataSource {
    fun createUsers(newUser: UserModel): Completable
    fun getUserData(): Single<UserModel>
    fun isUserExist(): Single<Boolean>
}
