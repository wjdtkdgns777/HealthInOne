package com.example.mysolelife.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

import com.example.mysolelife.R
import com.example.mysolelife.databinding.FragmentStoreBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

import java.util.*


class StoreFragment : Fragment() {
    private lateinit var binding: FragmentStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false)

        val user = Firebase.auth.currentUser

        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl

            // Update the views
            // For example:
            binding.userImage.setImageURI(photoUrl)
            binding.editUserName.setText(name)
            binding.editEmail.setText(email)
        }

        binding.btnChangeImage.setOnClickListener {
            // Here add your code to handle the image change.
        }

        binding.btnUpdateName.setOnClickListener {
            val newUserName = binding.editUserName.text.toString()
            val profileUpdates = userProfileChangeRequest {
                displayName = newUserName
            }

            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User name updated.")
                    }
                }
        }

        binding.btnUpdateEmail.setOnClickListener {
            val newEmail = binding.editEmail.text.toString()

            user!!.updateEmail(newEmail)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User email address updated.")
                    }
                }
        }


        binding.homeTab.setOnClickListener {
            it.findNavController().navigate(R.id.action_storeFragment_to_homeFragment)
        }

        binding.talkTab.setOnClickListener {
            it.findNavController().navigate(R.id.action_storeFragment_to_talkFragment)
        }

        binding.bookmarkTab.setOnClickListener {
            it.findNavController().navigate(R.id.action_storeFragment_to_bookmarkFragment)
        }

        binding.tipTab.setOnClickListener {
            it.findNavController().navigate(R.id.action_storeFragment_to_tipFragment)
        }

        return binding.root
    }


}