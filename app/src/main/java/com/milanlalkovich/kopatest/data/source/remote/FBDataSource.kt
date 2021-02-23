package com.milanlalkovich.kopatest.data.source.remote

import com.milanlalkovich.kopatest.domain.model.response.UserModel
import io.reactivex.Single

/**
 *  Created by Android Studio on 22.02.2021 17:02
 *  Developer: Dima Iakubenko
 */

interface FBDataSource {

    fun setUsers(newUser:UserModel): Single<List<UserModel>>
}
