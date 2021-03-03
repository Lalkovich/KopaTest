package com.milanlalkovich.kopatest.view.auth

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.milanlalkovich.kopatest.R
import com.milanlalkovich.kopatest.core.extensions.nonNullObserve
import com.milanlalkovich.kopatest.core.fragment.BaseVMFragment
import com.milanlalkovich.kopatest.databinding.FragmentAuthBinding
import kotlin.reflect.KClass

/**
 *  Created by Android Studio on 30.01.2021 16:20
 *  Developer: Dima Iakubenko
 */

class AuthFragment : BaseVMFragment<AuthViewModel, FragmentAuthBinding>() {

    companion object {
        private const val RC_SIGN_IN = 120
    }

    override val viewModelClass: KClass<AuthViewModel>
        get() = AuthViewModel::class
    override val layoutId: Int
        get() = R.layout.fragment_auth

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val callbackManager = CallbackManager.Factory.create()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.phoneAuth.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_phoneAuthFragment)
        }

        viewModel.isExist.nonNullObserve(viewLifecycleOwner) {
            if(it){
                findNavController().navigate(R.id.action_auth_complete)
            }else{
               findNavController().navigate(R.id.action_authFragment_to_dataCompletionFragment)
            }
        }


        auth = FirebaseAuth.getInstance()

        //Facebook Auth

        binding.facebookAuth.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(
                this,
                listOf("email", "public_profile")
            )
            LoginManager.getInstance().registerCallback(callbackManager, object :
                FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {

                }

                override fun onError(error: FacebookException) {
                    Log.d(TAG, "Facebook:OnError ", error)
                }
            })
        }


        //Google Auth Code

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding.googleAuth.setOnClickListener {
            signIn()
        }

    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e)
                    // ...
                }
            } else {
                Log.w(TAG, exception.toString())
            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    viewModel.isUserExist()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }

                // ...
            }
    }

    //Facebook code
    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    viewModel.isUserExist()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }

                // ...
            }
    }


}
