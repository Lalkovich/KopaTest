package com.milanlalkovich.kopatest.data.source.remote

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.milanlalkovich.kopatest.domain.model.response.Boots
import com.milanlalkovich.kopatest.domain.model.response.BootsModel
import com.milanlalkovich.kopatest.domain.model.response.UserModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.util.*

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

    override fun getBoots(): Single<List<Boots>> = Single.create {
        db.collection("boots")
            .whereEqualTo("archived",false)
            .get()
            .addOnSuccessListener { result ->
                val list: MutableList<Boots> = mutableListOf()
                for (document in result) {

                    list.add(
                        Boots(
                            id = document.id,
                            images = document["images"] as? List<String> ?: listOf(),
                            title = document["title"].toString(),
                            width = document["width"].toString().toDouble(),
                            length = document["length"].toString().toDouble(),
                            price = document["price"].toString().toDouble(),
                            bootsLength = document["bootsLength"].toString().toDouble(),
                            material = document["material"].toString(),
                            isArchived = document["archived"].toString().toBoolean(),
                            userUid = document["userUid"].toString()
                        )
                    )
                }
                it.onSuccess(list)
            }
            .addOnFailureListener { exception ->
                it.onError(exception)
            }
    }

    override fun getBootsById(id: String): Single<Boots> = Single.create {
        db.collection("boots")
            .document(id)
            .get()
            .addOnSuccessListener { result ->
                val boots = Boots(
                    id = result.id,
                    images = result["images"] as? List<String> ?: listOf(),
                    title = result["title"].toString(),
                    width = result["width"].toString().toDouble(),
                    price = result["price"].toString().toDouble(),
                    length = result["length"].toString().toDouble(),
                    bootsLength = result["bootsLength"].toString().toDouble(),
                    material = result["material"].toString(),
                    description = result["description"].toString(),
                    isArchived = result["archived"].toString().toBoolean(),
                    userUid = result["userUid"].toString()
                )
                it.onSuccess(boots)
            }
            .addOnFailureListener { exception ->
                it.onError(exception)
            }
    }

    override fun createBoots(boots: BootsModel): Completable = Completable.create {
        db.collection("boots")
            .add(boots)
            .addOnSuccessListener { result ->
                it.onComplete()
            }
            .addOnFailureListener { exception ->
                it.onError(exception)

            }
    }

    override fun getArchivedBoots(): Single<List<Boots>> = Single.create {
        db.collection("boots")
            .whereEqualTo("userUid", FirebaseAuth.getInstance().currentUser?.uid ?: "")
            .whereEqualTo("archived", true)
            .get()
            .addOnSuccessListener { result ->
                val list: MutableList<Boots> = mutableListOf()
                for (document in result) {

                    list.add(
                        Boots(
                            id = document.id,
                            images = document["images"] as? List<String> ?: listOf(),
                            title = document["title"].toString(),
                            width = document["width"].toString().toDouble(),
                            length = document["length"].toString().toDouble(),
                            price = document["price"].toString().toDouble(),
                            bootsLength = document["bootsLength"].toString().toDouble(),
                            material = document["material"].toString(),
                            isArchived = document["archived"].toString().toBoolean(),
                            userUid = document["userUid"].toString()
                        )
                    )
                }
                it.onSuccess(list)
            }
            .addOnFailureListener { exception ->
                it.onError(exception)
            }


    }

    override fun getActiveBoots(): Single<List<Boots>> = Single.create {
        db.collection("boots")
            .whereEqualTo("userUid", FirebaseAuth.getInstance().currentUser?.uid ?: "")
            .whereEqualTo("archived", false)
            .get()
            .addOnSuccessListener { result ->
                val list: MutableList<Boots> = mutableListOf()
                for (document in result) {

                    list.add(
                        Boots(
                            id = document.id,
                            images = document["images"] as? List<String> ?: listOf(),
                            title = document["title"].toString(),
                            width = document["width"].toString().toDouble(),
                            length = document["length"].toString().toDouble(),
                            price = document["price"].toString().toDouble(),
                            bootsLength = document["bootsLength"].toString().toDouble(),
                            material = document["material"].toString(),
                            isArchived = document["archived"].toString().toBoolean(),
                            userUid = document["userUid"].toString()
                        )
                    )
                }
                it.onSuccess(list)
            }
            .addOnFailureListener { exception ->
                it.onError(exception)
            }


    }

    override fun uploadImages(images: List<Uri>): Single<List<String>> {
        val storage: FirebaseStorage =
            FirebaseStorage.getInstance("gs://kopatest-f6e8d.appspot.com")
        val basePath = "image"
        val userFolder = FirebaseAuth.getInstance().currentUser?.uid!!

        return Observable.fromIterable(images)
            .flatMapSingle { uri ->
                Single.create<String> {
                    val imageName = UUID.randomUUID().toString()
                    val newImageReference: StorageReference = storage.reference.child(
                        "$basePath/$userFolder/$imageName"
                    )
                    val uploadTask = newImageReference.putFile(uri)
                    uploadTask.continueWithTask {
                        if (!uploadTask.isSuccessful) {
                            uploadTask.exception?.let {
                                throw it
                            }
                        }
                        newImageReference.downloadUrl
                    }.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            it.onSuccess(task.result.toString())
                        } else {
                            task.exception?.let {
                                throw it
                            }
                        }
                    }
                }.subscribeOn(Schedulers.io())
            }
            .toList()
    }

}