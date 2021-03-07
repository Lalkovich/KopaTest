package com.milanlalkovich.kopatest.view.boots

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.milanlalkovich.kopatest.core.view_model.BaseViewModel
import com.milanlalkovich.kopatest.domain.model.response.Boots
import com.milanlalkovich.kopatest.domain.repository.BootsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 *  Created by Android Studio on 18.02.2021 22:02
 *  Developer: Dima Iakubenko
 */

class BootsViewModel(private val apiRepository: BootsRepository) : BaseViewModel() {
    private val _boots = MutableLiveData<List<Boots>>()
    val boots: LiveData<List<Boots>> = _boots

    init {
        getBoots()
    }

    fun getBoots() {
        disposables + apiRepository.getBoots()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _boots.postValue(it)
                },
                onError = {
                    timber.log.Timber.d(it)
                }
            )

    }
}