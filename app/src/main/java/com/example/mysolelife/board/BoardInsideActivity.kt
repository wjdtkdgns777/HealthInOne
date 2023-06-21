package com.example.mysolelife.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.mysolelife.R
import com.example.mysolelife.databinding.ActivityBoardInsideBinding
import com.example.mysolelife.fragments.TalkFragment
import com.example.mysolelife.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class BoardInsideActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoardInsideBinding

    private val TAG = TalkFragment::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_inside)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)

        //첫번쨰 방법
//        val title = intent.getStringExtra("title").toString()
//        val content = intent.getStringExtra("content").toString()
//        val time = intent.getStringExtra("time").toString()
//
//
//        binding.titleArea.text = title
//        binding.textArea.text = content
//        binding.timeArea.text = time

        //두번째 방법

        val key = intent.getStringExtra("key")
//        Toast.makeText(this, key, Toast.LENGTH_LONG).show()
        getBoardData(key.toString())

    }

    private fun getBoardData(key: String) {

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val datModel = snapshot.getValue(BoardModel::class.java)

                binding.titleArea.text = datModel!!.title
                binding.textArea.text = datModel!!.content
                binding.timeArea.text = datModel!!.time

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "LoadPost:onCanceled", error.toException())
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)

    }
}