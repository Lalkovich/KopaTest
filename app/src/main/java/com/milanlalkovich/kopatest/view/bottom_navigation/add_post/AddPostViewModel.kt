package com.milanlalkovich.kopatest.view.bottom_navigation.add_post

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.milanlalkovich.kopatest.core.view_model.BaseViewModel
import com.milanlalkovich.kopatest.domain.model.response.BootsModel
import com.milanlalkovich.kopatest.domain.repository.BootsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 *  Created by Android Studio on 07.03.2021 16:29
 *  Developer: Dima Iakubenko
 */

class AddPostViewModel(private val bootsRepository: BootsRepository) : BaseViewModel() {
    private val _boots = MutableLiveData<Unit>()
    val boots: LiveData<Unit> = _boots

    fun createBoots(images: List<Uri>, boots: BootsModel) {
        disposables + bootsRepository.uploadImages(images)
            .flatMapCompletable {
                boots.images = it
                bootsRepository.createBoots(boots)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    _boots.postValue(Unit)
                },
                onError = {
                    timber.log.Timber.d(it)
                }
            )

    }
}