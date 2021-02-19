package com.milanlalkovich.kopatest.view.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.*
import com.milanlalkovich.kopatest.R

/**
 *  Created by Android Studio on 30.01.2021 15:46
 *  Developer: Dima Iakubenko
 */

class SplashFragment : Fragment() {
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = getInstance()

        val user = auth.currentUser

        Handler(Looper.getMainLooper())
            .postDelayed({
                if (user != null) {
                    findNavController().navigate(R.id.action_splashFragment_to_profileFragment)
                } else {
                    findNavController().navigate(R.id.action_splashFragment_to_authFragment)
                }
            }, 3000)
    }
}