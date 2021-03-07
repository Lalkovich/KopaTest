package com.milanlalkovich.kopatest.view.bottom_navigation.my_posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.milanlalkovich.kopatest.core.view_model.BaseViewModel
import com.milanlalkovich.kopatest.domain.model.response.Boots
import com.milanlalkovich.kopatest.domain.repository.BootsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 *  Created by Android Studio on 07.03.2021 19:32
 *  Developer: Dima Iakubenko
 */

class MyPostsViewModel(private val apiRepository: BootsRepository) : BaseViewModel() {
    private val _active = MutableLiveData<List<Boots>>()
    val active: LiveData<List<Boots>> = _active

    private val _archived = MutableLiveData<List<Boots>>()
    val archived: LiveData<List<Boots>> = _archived

    fun getActiveBoots(){
        disposables + apiRepository.getActiveBoots()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _active.postValue(it)
                },
                onError = {
                    timber.log.Timber.d(it)
                }
            )
    }

    fun getArchivedBoots(){
        disposables + apiRepository.getArchivedBoots()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _archived.postValue(it)
                },
                onError = {
                    timber.log.Timber.d(it)
                }
            )
    }
}