package com.milanlalkovich.kopatest.view.data_completion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.milanlalkovich.kopatest.core.view_model.BaseViewModel
import com.milanlalkovich.kopatest.domain.model.response.UserModel
import com.milanlalkovich.kopatest.domain.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 *  Created by Android Studio on 25.02.2021 17:19
 *  Developer: Dima Iakubenko
 */

class DataCompletionViewModel(private val userRepository: UserRepository) : BaseViewModel() {
    private val _userCreated = MutableLiveData<Unit>()
    val userCreated: LiveData<Unit> = _userCreated

    fun createUser(userModel: UserModel) {
        disposables + userRepository.createUsers(userModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    _userCreated.postValue(Unit)
                },
                onError = {
                    Timber.d(it)
                }
            )
    }
}