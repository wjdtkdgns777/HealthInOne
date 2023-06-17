package com.example.mysolelife.contentsList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mysolelife.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ContentListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_list)

        val items = ArrayList<ContentModel>()
        val rvAdapter = ContentRVAdapter(baseContext , items)


        val database = Firebase.database

        val category = intent.getStringExtra("category")


        if(category=="category1"){

            val myRef = database.getReference("contents")

            val postlistener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (dataModel in dataSnapshot.children) {
                        Log.d("ContentListActivity", dataModel.toString())
                        val item = dataModel.getValue(ContentModel::class.java)
                        items.add(item!!)
                    }
                    rvAdapter.notifyDataSetChanged()
                    Log.d("ContentListActivity",items.toString())
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message

                    Log.w("ContentListActivity", "LoadPost:onCancelled",databaseError.toException())
                }
            }
            myRef.addValueEventListener(postlistener)

        }else if(category=="category2"){

            val myRef = database.getReference("contents2")

            val postlistener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (dataModel in dataSnapshot.children) {
                        Log.d("ContentListActivity", dataModel.toString())
                        val item = dataModel.getValue(ContentModel::class.java)
                        items.add(item!!)
                    }
                    rvAdapter.notifyDataSetChanged()
                    Log.d("ContentListActivity",items.toString())
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message

                    Log.w("ContentListActivity", "LoadPost:onCancelled",databaseError.toException())
                }
            }
            myRef.addValueEventListener(postlistener)
        }


        val rv : RecyclerView = findViewById(R.id.rv)


        rv.adapter = rvAdapter

        rv.layoutManager = GridLayoutManager(this,2)
        rvAdapter.itemClick = object : ContentRVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                Toast.makeText(baseContext,items[position].title , Toast.LENGTH_LONG).show()

                val intent = Intent(this@ContentListActivity, ContentShowActivity::class.java)
                intent.putExtra("url",items[position].webUrl)
                startActivity(intent)
            }

        }

        val myRef2 = database.getReference("contents")

    }
}



//        myRef.push().setValue(
//            ContentModel("title1","http://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FOtaMq%2Fbtq67OMpk4W%2FH1cd0mda3n2wNWgVL9Dqy0%2Fimg.png","https://philosopher-chan.tistory.com/1249")
//
//        )
//        myRef.push().setValue(
//            ContentModel("title2","http://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FFtY3t%2Fbtq65q6P4Zr%2FWe64GM8KzHAlGE3xQ2nDjk%2Fimg.png","https://philosopher-chan.tistory.com/1248")
//        )
//        myRef.push().setValue(
//            ContentModel("title3","http://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FFtY3t%2Fbtq65q6P4Zr%2FWe64GM8KzHAlGE3xQ2nDjk%2Fimg.png","https://philosopher-chan.tistory.com/1248")
//        )

//        items.add(ContentModel("title1","http://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FOtaMq%2Fbtq67OMpk4W%2FH1cd0mda3n2wNWgVL9Dqy0%2Fimg.png","https://philosopher-chan.tistory.com/1249"))
//        items.add(ContentModel("title2","http://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FFtY3t%2Fbtq65q6P4Zr%2FWe64GM8KzHAlGE3xQ2nDjk%2Fimg.png","https://philosopher-chan.tistory.com/1248"))
//        items.add(ContentModel("title3","http://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FFtY3t%2Fbtq65q6P4Zr%2FWe64GM8KzHAlGE3xQ2nDjk%2Fimg.png","https://philosopher-chan.tistory.com/1248"))
