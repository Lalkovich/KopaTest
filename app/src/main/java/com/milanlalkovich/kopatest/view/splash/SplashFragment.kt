package com.milanlalkovich.kopatest.view.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.*
import com.milanlalkovich.kopatest.R
import com.milanlalkovich.kopatest.databinding.FragmentAuthBinding
import com.milanlalkovich.kopatest.databinding.FragmentSplashBinding

/**
 *  Created by Android Studio on 30.01.2021 15:46
 *  Developer: Dima Iakubenko
 */

class SplashFragment : Fragment() {
    private lateinit var auth:FirebaseAuth
    private lateinit var binding:FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = getInstance()

        val user = auth.currentUser

        Handler(Looper.getMainLooper())
            .postDelayed({
                if (user != null) {
                    findNavController().navigate(R.id.action_splashFragment_to_menuFragment)
                } else {
                    findNavController().navigate(R.id.action_splashFragment_to_authFragment)
                }
            },
                3000)
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

}