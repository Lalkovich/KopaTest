package com.milanlalkovich.kopatest.view.phone_auth

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.milanlalkovich.kopatest.R
import com.milanlalkovich.kopatest.core.extensions.nonNullObserve
import com.milanlalkovich.kopatest.core.fragment.BaseVMFragment
import com.milanlalkovich.kopatest.databinding.FragmentPhoneAuthBinding
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass


/**
 *  Created by Android Studio on 31.01.2021 17:12
 *  Developer: Dima Iakubenko
 */

class PhoneAuthFragment : BaseVMFragment<PhoneAuthViewModel, FragmentPhoneAuthBinding>() {

    override val viewModelClass: KClass<PhoneAuthViewModel>
        get() = PhoneAuthViewModel::class
    override val layoutId: Int
        get() = R.layout.fragment_phone_auth

    private lateinit var auth: FirebaseAuth
    private lateinit var verifyId: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.btnNumberVerify.setOnClickListener {
            if (validateNumber(binding.btnNumberVerify.text.toString())) {
                signIn()
                binding.editNumber.visibility = View.GONE
                binding.btnNumberVerify.visibility = View.GONE

                binding.btnCodeVerify.visibility = View.VISIBLE
                binding.editCode.visibility = View.VISIBLE
            }
        }

        viewModel.isExist.nonNullObserve(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_phoneAuthFragment_to_menuFragment )
            } else {
                findNavController().navigate(R.id.action_phoneAuthFragment_to_dataCompletionFragment )
            }
        }

        binding.btnCodeVerify.setOnClickListener {
            verifyCode(binding.editCode.text.toString())

        }

    }

    private fun validateNumber(input: String): Boolean {
        return input.isNotEmpty() && Patterns.PHONE.matcher(binding.editNumber.text.toString())
            .matches()

    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                       viewModel.isUserExist()

                    val user = task.result?.user
                    // ...
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                }
            }
    }

    private fun signIn() {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(binding.editNumber.text.toString())       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    val code = credential.smsCode
                    if (code != null) {
                        verifyCode(code)
                    } else {
                        signInWithPhoneAuthCredential(credential)
                    }

                }

                override fun onVerificationFailed(e: FirebaseException) {

                    if (e is FirebaseAuthInvalidCredentialsException) {

                    } else if (e is FirebaseTooManyRequestsException) {

                    }


                }

                override fun onCodeSent(
                    verificationId: String,
                    forceResendingToken: PhoneAuthProvider.ForceResendingToken
                ) {
                    verifyId = verificationId

                }
            })          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        auth.setLanguageCode("ua")
    }

    private fun verifyCode(code: String) {
        if (verifyId.isNotEmpty()) {
            val credential = PhoneAuthProvider.getCredential(verifyId, code)
            signInWithPhoneAuthCredential(credential)
        }
    }


}