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
                            width = document["width"].toString().toInt(),
                            length = document["length"].toString().toInt(),
                            price = document["price"].toString().toInt(),
                            bootsLength = document["bootsLength"].toString().toInt(),
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
                    width = result["width"].toString().toInt(),
                    price = result["price"].toString().toInt(),
                    bootsLength = result["bootsLength"].toString().toInt(),
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
                            width = document["width"].toString().toInt(),
                            length = document["length"].toString().toInt(),
                            price = document["price"].toString().toInt(),
                            bootsLength = document["bootsLength"].toString().toInt(),
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
                            width = document["width"].toString().toInt(),
                            length = document["length"].toString().toInt(),
                            price = document["price"].toString().toInt(),
                            bootsLength = document["bootsLength"].toString().toInt(),
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
        val storageRef = storage.reference
        val ref: StorageReference = storageRef.child("*image/" + UUID.randomUUID().toString())

        return Observable.fromIterable(images)
            .flatMapSingle { uri ->
                Single.create<String> {
                    val uploadTask = ref.putFile(uri)
                    uploadTask.continueWithTask { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                throw it
                            }
                        }
                        ref.downloadUrl
                    }.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            it.onSuccess(task.result.toString())
                        }
                    }
                }
            }.toList()
    }

}