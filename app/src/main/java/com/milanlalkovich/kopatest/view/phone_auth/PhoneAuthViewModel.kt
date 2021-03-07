package com.milanlalkovich.kopatest.view.phone_auth

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.milanlalkovich.kopatest.core.view_model.BaseViewModel
import com.milanlalkovich.kopatest.domain.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 *  Created by Android Studio on 25.02.2021 17:22
 *  Developer: Dima Iakubenko
 */

class PhoneAuthViewModel(private val userRepository: UserRepository):BaseViewModel() {

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