package com.example.mysolelife.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.mysolelife.R
import com.example.mysolelife.board.BoardWriteActivity
import com.example.mysolelife.databinding.FragmentTalkBinding
import com.example.mysolelife.databinding.FragmentTipBinding


class TalkFragment : Fragment() {
    private lateinit var binding : FragmentTalkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk, container, false)

        binding.writeBtn.setOnClickListener{
            val intent  = Intent(context , BoardWriteActivity::class.java)
            startActivity(intent)
        }

        binding.homeTab.setOnClickListener{
            it.findNavController().navigate(R.id.action_talkFragment_to_homeFragment)
        }

        binding.tipTab.setOnClickListener{
            it.findNavController().navigate(R.id.action_talkFragment_to_tipFragment)
        }

        binding.bookmarkTab.setOnClickListener{
            it.findNavController().navigate(R.id.action_talkFragment_to_bookmarkFragment)
        }

        binding.storeTab.setOnClickListener{
            it.findNavController().navigate(R.id.action_talkFragment_to_storeFragment)
        }

        return binding.root
    }
}