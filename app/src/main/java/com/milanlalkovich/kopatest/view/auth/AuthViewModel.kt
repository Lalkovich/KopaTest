package com.milanlalkovich.kopatest.view.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.milanlalkovich.kopatest.core.view_model.BaseViewModel
import com.milanlalkovich.kopatest.domain.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 *  Created by Android Studio on 18.02.2021 21:45
 *  Developer: Dima Iakubenko
 */

class AuthViewModel(private val userRepository: UserRepository): BaseViewModel() {
    private val _isExist = MutableLiveData<Boolean>()
    val isExist: LiveData<Boolean> = _isExist

    fun isUserExist(){
        disposables + userRepository.isUserExist()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _isExist.postValue(it)
                },
                onError = {timber.log.Timber.d(it)}
            )
    }
}