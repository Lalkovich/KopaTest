package com.milanlalkovich.kopatest.view.profile

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.milanlalkovich.kopatest.R
import com.milanlalkovich.kopatest.core.extensions.nonNullObserve
import com.milanlalkovich.kopatest.core.fragment.BaseVMFragment
import com.milanlalkovich.kopatest.databinding.FragmentProfileBinding
import kotlin.reflect.KClass

/**
 *  Created by Android Studio on 04.02.2021 19:45
 *  Developer: Dima Iakubenko
 */

class ProfileFragment : BaseVMFragment<ProfileViewModel, FragmentProfileBinding>() {

    private lateinit var auth: FirebaseAuth

    override val layoutId: Int
        get() = R.layout.fragment_profile

    override val viewModelClass: KClass<ProfileViewModel> = ProfileViewModel::class


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        viewModel.users.nonNullObserve(viewLifecycleOwner){
            binding.profileName.text = "${it.fname} ${it.sname}"
            binding.userCity.text = it.city
            binding.userNumber.text = it.number
        }

        val user = auth.currentUser

        Glide.with(this).load(user?.photoUrl).transform(CircleCrop()).into(binding.profileFace)

        binding.profileExitButton.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_signOut)
        }
    }


}