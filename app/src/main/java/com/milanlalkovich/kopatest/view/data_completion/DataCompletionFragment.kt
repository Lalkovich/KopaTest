package com.milanlalkovich.kopatest.view.data_completion

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.milanlalkovich.kopatest.R
import com.milanlalkovich.kopatest.core.extensions.nonNullObserve
import com.milanlalkovich.kopatest.core.fragment.BaseVMFragment
import com.milanlalkovich.kopatest.databinding.FragmentDataCompletionBinding
import com.milanlalkovich.kopatest.domain.model.response.UserModel
import kotlin.reflect.KClass

/**
 *  Created by Android Studio on 31.01.2021 19:16
 *  Developer: Dima Iakubenko
 */

class DataCompletionFragment :
    BaseVMFragment<DataCompletionViewModel, FragmentDataCompletionBinding>() {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth

    override val layoutId: Int
        get() = R.layout.fragment_data_completion

    override val viewModelClass: KClass<DataCompletionViewModel> = DataCompletionViewModel::class

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser?.displayName.isNullOrEmpty().not()) {
            val splitName = auth.currentUser?.displayName?.split(' ')
            binding.fname.setText(splitName?.get(0) ?: "")
            binding.sname.setText(splitName?.get(1) ?: "")
        }
        if(auth.currentUser?.phoneNumber.isNullOrEmpty().not())
        {
            binding.numberEdit.setText(auth.currentUser?.phoneNumber)
        }

        viewModel.userCreated.nonNullObserve(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_dataCompletionFragment_to_menuFragment)
        }
        binding.btnAuthCompletion.setOnClickListener {
            val newUser = UserModel(
                fname = binding.fname.text.toString(),
                sname = binding.sname.text.toString(),
                number = binding.numberEdit.text.toString(),
                city = binding.city.text.toString()
            )

            fun isDataFull() {
                if (newUser.fname.isEmpty()) {
                    binding.fname.error = "Введите имя"
                } else if (newUser.sname.isEmpty()) {
                    binding.sname.error = "Введите фамилию"
                } else if (newUser.city.isEmpty()) {
                    binding.city.error = "Введите название города"
                } else if (newUser.number.isEmpty()) {
                    binding.numberEdit.error = "Введите свой номер"
                } else {
                    viewModel.createUser(newUser)
                }
            }
            isDataFull()
        }
    }
}