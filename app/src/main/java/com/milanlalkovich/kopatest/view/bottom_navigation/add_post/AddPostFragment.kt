package com.milanlalkovich.kopatest.view.bottom_navigation.add_post

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.milanlalkovich.kopatest.R
import com.milanlalkovich.kopatest.core.extensions.nonNullObserve
import com.milanlalkovich.kopatest.core.fragment.BaseVMFragment
import com.milanlalkovich.kopatest.databinding.FragmentAddPostBinding
import com.milanlalkovich.kopatest.domain.entity.ImageEntity
import com.milanlalkovich.kopatest.domain.model.response.BootsModel
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.reflect.KClass


/**
 *  Created by Android Studio on 05.03.2021 12:41
 *  Developer: Dima Iakubenko
 */

class AddPostFragment : BaseVMFragment<AddPostViewModel, FragmentAddPostBinding>() {
    override val viewModelClass: KClass<AddPostViewModel>
        get() = AddPostViewModel::class
    override val layoutId: Int
        get() = R.layout.fragment_add_post

    private val adapter = ImageAdapter {}
    private val pickImage: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
            image.first { it is ImageEntity.Image && it.imageUri == null }.apply {
                (this as ImageEntity.Image).imageUri = imageUri
            }
            adapter.list = image
        }

    private val image = listOf(
        ImageEntity.SelectImage, ImageEntity.Image(),
        ImageEntity.Image(), ImageEntity.Image(), ImageEntity.Image(),
        ImageEntity.Image(), ImageEntity.Image(), ImageEntity.Image()
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserves()
        val storage: FirebaseStorage =
            FirebaseStorage.getInstance("gs://kopatest-f6e8d.appspot.com")
        val storageRef = storage.reference
        var imagesRef: StorageReference? = storageRef.child("images/")

        binding.selectImage.layoutManager = GridLayoutManager(requireContext(), 4)
        binding.selectImage.adapter = adapter
        adapter.list = image

        adapter.setOnAddImageListener {
            pickImage.launch("image/*")
        }

        binding.save.setOnClickListener {
            var ref:StorageReference = storageRef.child("*image/" + UUID.randomUUID().toString())
            image.forEach{
                if(it is ImageEntity.Image)
                {
                    if(it.imageUri != null){
                        ref.putFile(it.imageUri!!)
                    }
                }
            }
            viewModel.createBoots(
                BootsModel(
                    description = binding.etDescription.text.toString(),
                    material = binding.spinnerMaterial.selectedItem.toString(),
                    price = binding.etPrice.text.toString().toInt(),
                    title = binding.etModel.text.toString(),
                    length = binding.spinnerSize.selectedItem.toString().toInt(),
                    width = binding.spinnerWidth.selectedItem.toString().toInt(),
                    bootsLength = binding.spinnerLength.selectedItem.toString().toInt(),
                    isArchived = false,
                    userUid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                )
            )
        }
    }


    private fun initObserves() {
        viewModel.boots.nonNullObserve(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

}