package com.example.mysolelife.fragments
import android.app.AlertDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.navigation.findNavController
import com.example.mysolelife.R
import com.example.mysolelife.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var binding : FragmentHomeBinding
    private var uid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser != null) {
            uid = firebaseUser.uid
            database = FirebaseDatabase.getInstance().getReference("exerciseRecords/$uid")
        } else {
            // No user is signed in
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)



        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = "$year-${month + 1}-$dayOfMonth"
            database.child(date).get().addOnSuccessListener {
                showExerciseDialog(date, it.value as? String)
            }
        }
        binding.talkTab.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_talkFragment)
        }


        binding.storeTab.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_storeFragment)
        }

        binding.chatButton.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_chatFragment)
        }

        return binding.root
    }
    private fun showExerciseDialog(date: String, exerciseRecord: String?) {
        val exerciseEditText = EditText(context).apply {
            setText(exerciseRecord ?: "")
        }

        AlertDialog.Builder(context)
            .setTitle("운동 기록")
            .setMessage("$date 의 운동을 기록하세요.")
            .setView(exerciseEditText)
            .setPositiveButton("저장") { _, _ ->
                database.child(date).setValue(exerciseEditText.text.toString())
            }
            .setNegativeButton("취소", null)
            .show()
    }
}

