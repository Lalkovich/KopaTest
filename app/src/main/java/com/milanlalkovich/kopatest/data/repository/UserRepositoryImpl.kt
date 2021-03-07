package com.milanlalkovich.kopatest.data.repository

import com.milanlalkovich.kopatest.data.source.remote.FBDataSource
import com.milanlalkovich.kopatest.domain.model.response.UserModel
import com.milanlalkovich.kopatest.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Single

/**
 *  Created by Android Studio on 26.02.2021 12:32
 *  Developer: Dima Iakubenko
 */

class UserRepositoryImpl(private val firebaseDataSource: FBDataSource) : UserRepository {
    override fun createUsers(newUser: UserModel): Completable =
        firebaseDataSource.createUsers(newUser)

    override fun getUserData(): Single<UserModel> = firebaseDataSource.getUserData()

    override fun isUserExist(): Single<Boolean> = firebaseDataSource.isUserExist()

}