package com.milanlalkovich.kopatest.domain.repository

import com.milanlalkovich.kopatest.domain.model.response.Boots
import com.milanlalkovich.kopatest.domain.model.response.BootsModel
import io.reactivex.Completable
import io.reactivex.Single

/**
 *  Created by Android Studio on 18.02.2021 21:56
 *  Developer: Dima Iakubenko
 */

interface BootsRepository {
    fun getBoots() : Single<List<Boots>>
    fun getBootsById(id: String) : Single<Boots>
    fun createBoots(boots: BootsModel) : Completable
    fun getArchivedBoots(): Single<List<Boots>>
    fun getActiveBoots(): Single<List<Boots>>
}