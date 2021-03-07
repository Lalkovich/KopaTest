package com.milanlalkovich.kopatest.data.repository

import com.milanlalkovich.kopatest.data.source.remote.FBDataSource
import com.milanlalkovich.kopatest.domain.model.response.Boots
import com.milanlalkovich.kopatest.domain.model.response.BootsModel
import com.milanlalkovich.kopatest.domain.repository.BootsRepository
import io.reactivex.Completable
import io.reactivex.Single

/**
 *  Created by Android Studio on 02.03.2021 17:34
 *  Developer: Dima Iakubenko
 */

class BootsRepositoryImpl(private val firebaseDataSource: FBDataSource) : BootsRepository {
    override fun getBoots(): Single<List<Boots>> = firebaseDataSource.getBoots()
    override fun getBootsById(id: String): Single<Boots> = firebaseDataSource.getBootsById(id)
    override fun createBoots(boots: BootsModel): Completable = firebaseDataSource.createBoots(boots)
    override fun getArchivedBoots(): Single<List<Boots>> = firebaseDataSource.getArchivedBoots()
    override fun getActiveBoots(): Single<List<Boots>> = firebaseDataSource.getActiveBoots()
}