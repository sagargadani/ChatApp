package com.example.assignment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.adapters.ChatMessageAdapter
import com.example.assignment.application.SharedPreferenceUtils
import com.example.assignment.utils.AppConstants
import com.example.assignment.utils.Botmodel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ChatActivity : AppCompatActivity(), View.OnClickListener {

    var mListView: RecyclerView? = null
    var mEditTextMessage: EditText? = null
    var btn_send: ImageView? = null
    var alChatData: ArrayList<Botmodel.BotNames.Messages>? = null
    private var mAdapter: ChatMessageAdapter? = null
    var bot_name: String? = null
    var botmodel: Botmodel? = null
    var selectedBot: Botmodel.BotNames? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mListView = findViewById(R.id.recycleChatList)
        mEditTextMessage = findViewById(R.id.et_message)

        btn_send = findViewById(R.id.btn_send)
        btn_send!!.setOnClickListener(this@ChatActivity)

        bot_name = intent.getStringExtra(AppConstants.IntentKeys.BOT_NAME)
        supportActionBar?.setTitle(bot_name)

        var chatdata =
            SharedPreferenceUtils.preferenceGetString(AppConstants.SharedPreferenceKeys.CHAT_DATA)
        botmodel = Gson().fromJson(chatdata, Botmodel::class.java)
        alChatData = ArrayList()

        for (item in botmodel!!.botNames) {
            if (item.name.lowercase().trim().equals(bot_name!!.lowercase().trim(), true)) {
                selectedBot = item
                break
            }
        }

        alChatData!!.addAll(
            Gson().fromJson(
                Gson().toJson(selectedBot?.messages),
                object : TypeToken<List<Botmodel.BotNames.Messages?>?>() {}.type
            )
        )

        mAdapter = ChatMessageAdapter(this@ChatActivity, alChatData, bot_name)
        val linearLayoutManager = LinearLayoutManager(this@ChatActivity)
        mListView!!.layoutManager = linearLayoutManager
        mListView!!.adapter = mAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_send -> {
                var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val currentCal = Calendar.getInstance()
                val message = mEditTextMessage!!.text.toString().trim()
                mEditTextMessage!!.setText("")
                var sendMessage =
                    Botmodel.BotNames.Messages("user", message, sdf.format(currentCal.getTime()))
                selectedBot?.last_message = message
                selectedBot?.last_message_date = sdf.format(currentCal.getTime())
                alChatData?.add(sendMessage)

//                for (item in botmodel!!.botNames) {
//                    if (item.name.lowercase().trim().equals(bot_name!!.lowercase().trim(), true)) {
//                        item.messages.clear();
//                        item.messages?.addAll(alChatData!!)
//                        break
//                    }
//                }
                for (i in 0..botmodel!!.botNames.size - 1) {
                    if (botmodel!!.botNames[i].name.lowercase().trim()
                            .equals(bot_name!!.lowercase().trim(), true)
                    ) {
                        botmodel!!.botNames[i].messages.clear();
                        botmodel!!.botNames[i].messages?.addAll(alChatData!!)
                        break
                    }
                }

                SharedPreferenceUtils.preferencePutString(
                    AppConstants.SharedPreferenceKeys.CHAT_DATA,
                    Gson().toJson(botmodel)
                )
                mAdapter?.notifyDataSetChanged()
                mListView?.scrollToPosition(alChatData?.size!!-1)

                Executors.newSingleThreadScheduledExecutor().schedule({
                    val recCal = Calendar.getInstance()
                    var receiveMessage = Botmodel.BotNames.Messages(
                        "bot",
                        "$message $message",
                        sdf.format(recCal.getTime())
                    )
                    selectedBot?.last_message = "$message $message"
                    selectedBot?.last_message_date = sdf.format(recCal.getTime())
                    alChatData?.add(receiveMessage)

//                    for (item in botmodel!!.botNames) {
//                        if (item.name.lowercase().trim()
//                                .equals(bot_name!!.lowercase().trim(), true)
//                        ) {
//                            item.messages.clear();
//                            item.messages?.addAll(alChatData!!)
//                            break
//                        }
//                    }
                    for (i in 0..botmodel!!.botNames.size - 1) {
                        if (botmodel!!.botNames[i].name.lowercase().trim()
                                .equals(bot_name!!.lowercase().trim(), true)
                        ) {
                            botmodel!!.botNames[i].messages.clear();
                            botmodel!!.botNames[i].messages?.addAll(alChatData!!)
                            break
                        }
                    }

                        SharedPreferenceUtils.preferencePutString(
                        AppConstants.SharedPreferenceKeys.CHAT_DATA,
                        Gson().toJson(botmodel)
                    )
                    mAdapter?.notifyDataSetChanged()
                    mListView?.scrollToPosition(alChatData?.size!!-1)


                }, 2, TimeUnit.SECONDS)


            }
        }
    }
}