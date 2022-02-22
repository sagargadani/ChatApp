package com.example.assignment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.adapters.ChatMessageAdapter.ChatViewHolder
import com.example.assignment.utils.Botmodel

class ChatMessageAdapter(
    context: Context,
    messagesArrayList: ArrayList<Botmodel.BotNames.Messages>?,
    user_name: String?
) : RecyclerView.Adapter<ChatViewHolder>() {


    var messagesArrayList: ArrayList<Botmodel.BotNames.Messages>? = null
    var user_name: String?=""
    var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.raw_chat_item_display, parent, false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messagesArrayList!!.size
    }

    override fun onBindViewHolder(viewHolder: ChatViewHolder, position: Int) {
        val relativeParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        if (position == 0) {
            relativeParams.setMargins(0, 40, 0, 0)
        } else {
            relativeParams.setMargins(0, 0, 0, 0)
        }
        viewHolder.rlSent.layoutParams = relativeParams
        if (messagesArrayList!![position].sender.equals("user", ignoreCase = true)) {
            viewHolder.rlSent.visibility = View.VISIBLE
            viewHolder.rlReceive.visibility = View.GONE
            viewHolder.txtSentText.text = messagesArrayList!![position].message
            viewHolder.txtSentTime.text = messagesArrayList!![position].messdate
        } else {
            viewHolder.rlSent.visibility = View.GONE
            viewHolder.rlReceive.visibility = View.VISIBLE
            viewHolder.txtReceiveText.text = messagesArrayList!![position].message
            viewHolder.txtReceiveTime.text = messagesArrayList!![position].messdate

//            viewHolder.cImgReceiveUser.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
        }
    }

    inner class ChatViewHolder(convertView: View) : RecyclerView.ViewHolder(convertView) {
        var rlSent: RelativeLayout
        var sentChatMessageView: RelativeLayout
        var llSentChatText: LinearLayout
        var txtSentText: TextView
        var txtSentTime: TextView
        var cImgSentUser: ImageView
        var rlReceive: RelativeLayout
        var cImgReceiveUser: ImageView
        var receiveChatMessageView: RelativeLayout
        var txtReceiveText: TextView
        var txtReceiveTime: TextView

        init {
            rlSent = convertView.findViewById(R.id.rlSent)
            cImgSentUser = convertView.findViewById(R.id.cImgSentUser)
            sentChatMessageView = convertView.findViewById(R.id.sentChatMessageView)
            llSentChatText = convertView.findViewById(R.id.llSentChatText)
            txtSentText = convertView.findViewById(R.id.txtSentText)
            txtSentTime = convertView.findViewById(R.id.txtSentTime)
            rlReceive = convertView.findViewById(R.id.rlReceive)
            cImgReceiveUser = convertView.findViewById(R.id.cImgReceiveUser)
            receiveChatMessageView = convertView.findViewById(R.id.receiveChatMessageView)
            txtReceiveText = convertView.findViewById(R.id.txtReceiveText)
            txtReceiveTime = convertView.findViewById(R.id.txtReceiveTime)
        }
    }

//    companion object {
//        private lateinit var ctx: Context
//    }

    init {
        this.context = context
        this.messagesArrayList = messagesArrayList
        this.user_name = user_name
    }
}