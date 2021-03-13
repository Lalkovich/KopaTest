package com.milanlalkovich.kopatest.view.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.milanlalkovich.kopatest.R
import com.milanlalkovich.kopatest.core.extensions.nonNullObserve
import com.milanlalkovich.kopatest.core.fragment.BaseVMFragment
import com.milanlalkovich.kopatest.databinding.FragmentProfileBinding
import com.milanlalkovich.kopatest.view.bottom_navigation.menu.MenuFragment
import kotlin.reflect.KClass


/**
 *  Created by Android Studio on 04.02.2021 19:45
 *  Developer: Dima Iakubenko
 */

class ProfileFragment : BaseVMFragment<ProfileViewModel, FragmentProfileBinding>() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

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
           if(user?.photoUrl == null){
               Glide.with(this).load("https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg").transform(
                   CircleCrop()
               ).into(binding.profileFace)
           }else{
               Glide.with(this).load(user?.photoUrl).transform(CircleCrop()).into(binding.profileFace)
           }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient= GoogleSignIn.getClient(requireActivity(), gso)

        binding.profileExitButton.setOnClickListener {
            auth.signOut()
            googleSignInClient.signOut()
            LoginManager.getInstance().logOut()
            Toast.makeText(context?.applicationContext, "Sign out", Toast.LENGTH_SHORT).show()
            ((parentFragment as NavHostFragment).parentFragment as MenuFragment).navigateToLogin()
        }

    }

}