package com.example.mysolelife.board

import android.content.ContentValues.TAG
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.mysolelife.R
import com.example.mysolelife.databinding.ActivityBoardWriteBinding
import com.example.mysolelife.utils.FBAuth
import com.example.mysolelife.utils.FBref

class BoardWriteActivity : AppCompatActivity() {

    private val TAG = BoardWriteActivity::class.java.simpleName

    private lateinit var binding :ActivityBoardWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this , R.layout.activity_board_write)

        binding.writeBtn.setOnClickListener {
            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val uid =  FBAuth.getUid()
            val time = FBAuth.getTime()

            Log.d(TAG , title)
            Log.d(TAG,content)

            FBref.boardRef
                .push()
                .setValue(BoardModel(title,content,uid,time))

            Toast.makeText(this,"게시글 입력 완료",Toast.LENGTH_LONG).show()
            finish()
        }
    }
}