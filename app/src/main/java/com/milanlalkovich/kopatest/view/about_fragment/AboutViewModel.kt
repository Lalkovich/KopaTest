package com.milanlalkovich.kopatest.view.about_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.milanlalkovich.kopatest.core.view_model.BaseViewModel
import com.milanlalkovich.kopatest.domain.model.response.Boots
import com.milanlalkovich.kopatest.domain.repository.BootsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 *  Created by Android Studio on 07.03.2021 13:44
 *  Developer: Dima Iakubenko
 */

class AboutViewModel(private val bootsRepository: BootsRepository) : BaseViewModel() {
    private val _boots = MutableLiveData<Boots>()
    val boots: LiveData<Boots> = _boots

    fun getBootsById(id: String) {
        disposables + bootsRepository.getBootsById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _boots.postValue(it)
                    Timber.d(it.toString())
                },
                onError = {
                    timber.log.Timber.d(it)
                }
            )

    }
}
