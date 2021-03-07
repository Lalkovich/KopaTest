package com.milanlalkovich.kopatest.data.source.remote

import com.milanlalkovich.kopatest.domain.model.response.Boots
import com.milanlalkovich.kopatest.domain.model.response.BootsModel
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
    fun getBoots(): Single<List<Boots>>
    fun getBootsById(id: String) : Single<Boots>
    fun createBoots(boots: BootsModel) : Completable
    fun getArchivedBoots(): Single<List<Boots>>
    fun getActiveBoots(): Single<List<Boots>>
}
