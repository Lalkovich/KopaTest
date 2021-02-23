package com.milanlalkovich.kopatest.view.data_completion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.milanlalkovich.kopatest.R
import com.milanlalkovich.kopatest.databinding.FragmentDataCompletionBinding
import com.milanlalkovich.kopatest.domain.model.response.UserModel
import timber.log.Timber

/**
 *  Created by Android Studio on 31.01.2021 19:16
 *  Developer: Dima Iakubenko
 */

class DataCompletionFragment : Fragment() {
    private lateinit var binding: FragmentDataCompletionBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDataCompletionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAuthCompletion.setOnClickListener {
            val newUser = UserModel(
                fname = binding.fname.text.toString(),
                sname = binding.sname.text.toString(),
                number = binding.numberEdit.text.toString(),
                city = binding.city.text.toString()
            )
            db.collection("users")
                .document(FirebaseAuth.getInstance().currentUser?.uid ?: "")
                .set(newUser)
                .addOnSuccessListener {
                    findNavController().navigate(R.id.action_dataCompletionFragment_to_profileFragment)
                }
                .addOnFailureListener {
                    Timber.e(it)
                }
        }
    }
}