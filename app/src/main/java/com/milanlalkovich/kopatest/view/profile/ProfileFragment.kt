package com.milanlalkovich.kopatest.view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.milanlalkovich.kopatest.R
import com.milanlalkovich.kopatest.databinding.FragmentAuthBinding
import com.milanlalkovich.kopatest.databinding.FragmentProfileBinding

/**
 *  Created by Android Studio on 04.02.2021 19:45
 *  Developer: Dima Iakubenko
 */

class ProfileFragment : Fragment() {

    private lateinit var binding:FragmentProfileBinding
    private lateinit var auth:FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser
        binding.profileName.text = user?.displayName
        binding.userNumber.text = user?.phoneNumber

        Glide.with(this).load(user?.photoUrl).transform(CircleCrop()).into(binding.profileFace)

        binding.profileExitButton.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_signOut)
        }
    }





}