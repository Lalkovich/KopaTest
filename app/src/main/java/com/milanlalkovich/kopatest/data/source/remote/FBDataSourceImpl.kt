package com.milanlalkovich.kopatest.data.source.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.milanlalkovich.kopatest.domain.model.response.UserModel
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Retrofit

/**
 *  Created by Android Studio on 20.02.2021 14:12
 *  Developer: Dima Iakubenko
 */

class FBDataSourceImpl(retrofit: Retrofit) : FBDataSource {
    private val db = FirebaseFirestore.getInstance()

    override fun createUsers(newUser: UserModel): Completable = Completable.create { emitter ->
        db.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid ?: "")
            .set(newUser)
            .addOnSuccessListener {
                emitter.onComplete()
            }
            .addOnFailureListener {
                emitter.onError(it)
            }

    }

    override fun getUserData(): Single<UserModel> = Single.create { emitter ->
        db.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid ?: "")
            .get()
            .addOnSuccessListener { document ->
                val userData = UserModel(
                    fname = document["fname"].toString(),
                    sname = document["sname"].toString(),
                    number = document["number"].toString(),
                    city = document["city"].toString()
                )
                emitter.onSuccess(userData)
            }
            .addOnFailureListener {
                emitter.onError(it)
            }
    }

    override fun isUserExist(): Single<Boolean> = Single.create { emitter ->

        db.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid ?: "")
            .get()
            .addOnSuccessListener {
                emitter.onSuccess(it.exists())
            }
            .addOnFailureListener {
                emitter.onError(it)
            }

    }


}