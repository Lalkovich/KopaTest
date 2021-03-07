package com.milanlalkovich.kopatest.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.milanlalkovich.kopatest.core.view_model.BaseViewModel
import com.milanlalkovich.kopatest.domain.model.response.UserModel
import com.milanlalkovich.kopatest.domain.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 *  Created by Android Studio on 02.03.2021 16:16
 *  Developer: Dima Iakubenko
 */

class ProfileViewModel(private val userRepository: UserRepository) : BaseViewModel() {
    private val _users = MutableLiveData<UserModel>()
    val users: LiveData<UserModel> = _users

    init {
        getUserData()
    }

    fun getUserData() {
        disposables + userRepository.getUserData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _users.postValue(it)
                },
                onError = {
                    timber.log.Timber.d(it)
                }

            )

    }
}