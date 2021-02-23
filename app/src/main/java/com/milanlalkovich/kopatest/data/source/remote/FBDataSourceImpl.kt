package com.milanlalkovich.kopatest.data.source.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.milanlalkovich.kopatest.domain.model.response.User
import com.milanlalkovich.kopatest.domain.model.response.UserModel
import io.reactivex.Single

/**
 *  Created by Android Studio on 20.02.2021 14:12
 *  Developer: Dima Iakubenko
 */

class FBDataSourceImpl : FBDataSource {
    private val db = FirebaseFirestore.getInstance()

    override fun setUsers(newUser:UserModel): Single<List<UserModel>> = Single.create {
        db.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid ?: "")
            .set(newUser)

    }



}