package com.example.mysolelife.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.chatgpt.Message
import com.example.chatgpt.User
import com.example.mysolelife.R
import com.example.mysolelife.databinding.FragmentStoreBinding
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.messages.MessagesList
import com.stfalcon.chatkit.messages.MessagesListAdapter
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class StoreFragment : Fragment() {
    private lateinit var binding: FragmentStoreBinding


    lateinit var editText: EditText;
    lateinit var messagesList: MessagesList
    lateinit var us: User
    lateinit var chatgpt: User
    lateinit var adapter: MessagesListAdapter<Message>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false)


        val v: View = inflater.inflate(R.layout.fragment_store, container, false)


        editText = v.findViewById(R.id.editTextTextPersonName)
        messagesList = v.findViewById(R.id.messagesList)

        var imageLoader: ImageLoader = object : ImageLoader {
            override fun loadImage(imageView: ImageView?, url: String?, payload: Any?) {
            }

        }
        adapter = MessagesListAdapter<Message>("1", imageLoader)
        messagesList.setAdapter(adapter)

        us = User("1", "jsh", "")
        chatgpt = User("2", "ChatGPT", "")

        binding.imageButton.setOnClickListener {
            var message: Message =
                Message("m1", editText.text.toString(), us, Calendar.getInstance().time)
            adapter.addToStart(message, true)
            performAction(editText.text.toString())
            editText.text.clear()
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

    fun performAction(input: String) {
        // Instantiate the RequestQueue.

        print(input)
        val queue = Volley.newRequestQueue(requireContext())
        val url = "https://api.openai.com/v1/chat/completions"

        val jsonObject = JSONObject()

        val jsonArray = JSONArray("[{\"role\": \"user\", \"content\": \"$input\"}]")
        jsonObject.put("messages", jsonArray)
        jsonObject.put("model", "gpt-3.5-turbo")


// Request a string response from the provided URL.
        val stringRequest = object : JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            Response.Listener<JSONObject> { response ->
                // Display the first 500 characters of the response string.


                var answer =
                    response.getJSONArray("choices").getJSONObject(0).getJSONObject("message")
                        .getString("content")
                var message = Message(
                    "M2",
                    answer.trim { it <= ' ' },
                    chatgpt,
                    Calendar.getInstance().time,

                    )
                adapter.addToStart(message, true)
            },
            Response.ErrorListener { }) {
            override fun getHeaders(): MutableMap<String, String> {
                var map = HashMap<String, String>()
                map.put("Content-Type", "application/json")
                map.put(
                    "Authorization",
                    "Bearer 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"
                )
                return map
            }
        }
        stringRequest.setRetryPolicy(object : RetryPolicy {
            override fun getCurrentTimeout(): Int {
                return 60000;
            }

            override fun getCurrentRetryCount(): Int {
                return 5;
            }

            override fun retry(error: VolleyError?) {

            }

        })

// Add the request to the RequestQueue.
        queue.add(stringRequest)

    }


}