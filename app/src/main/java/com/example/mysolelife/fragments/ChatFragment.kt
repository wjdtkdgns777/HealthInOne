package com.example.mysolelife.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.chatgpt.Message
import com.example.chatgpt.User
import com.example.mysolelife.R
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.messages.MessagesList
import com.stfalcon.chatkit.messages.MessagesListAdapter
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class ChatFragment : Fragment() {

    lateinit var sendBtn: ImageButton
    lateinit var editText: EditText
    lateinit var messagesList: MessagesList
    lateinit var us: User
    lateinit var chatgpt: User
    lateinit var adapter: MessagesListAdapter<Message>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        sendBtn = view.findViewById(R.id.imageButton2)

        editText = view.findViewById(R.id.editTextTextPersonName2)
        messagesList = view.findViewById(R.id.messagesList2)

        var imageLoader: ImageLoader = object : ImageLoader {
            override fun loadImage(imageView: ImageView?, url: String?, payload: Any?) {
            }
        }
        adapter = MessagesListAdapter<Message>("1", imageLoader)
        messagesList.setAdapter(adapter)

        us = User("1", "jsh", "")
        chatgpt = User("2", "ChatGPT", "")

        sendBtn.setOnClickListener {
            var message: Message = Message("m1", editText.text.toString(), us, Calendar.getInstance().time)
            adapter.addToStart(message, true)
            performAction(editText.text.toString())
            editText.text.clear()
        }

        return view
    }

    fun performAction(input: String) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(requireContext())
        val url = "https://api.openai.com/v1/chat/completions"

        val jsonObject = JSONObject()
        val jsonArray = JSONArray("[{\"role\": \"user\", \"content\": \"$input\"}]")
        jsonObject.put("messages", jsonArray)
        jsonObject.put("model", "gpt-4")

        val stringRequest = object : JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            Response.Listener<JSONObject> { response ->
                var answer = response.getJSONArray("choices").getJSONObject(0).getJSONObject("message")
                    .getString("content")
                var message = Message(
                    "M2",
                    answer.trim { it <= ' ' },
                    chatgpt,
                    Calendar.getInstance().time
                )
                adapter.addToStart(message, true)
            },
            Response.ErrorListener { }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                var map = HashMap<String, String>()
                map.put("Content-Type", "application/json")
                map.put("Authorization", "Bearer 여기에 api키 입력하세요")
                return map
            }
        }
        stringRequest.retryPolicy = object : RetryPolicy {
            override fun getCurrentTimeout(): Int {
                return 60000
            }

            override fun getCurrentRetryCount(): Int {
                return 15
            }

            override fun retry(error: VolleyError?) {
            }
        }

        queue.add(stringRequest)
    }
}
