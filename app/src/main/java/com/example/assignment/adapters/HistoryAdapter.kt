package com.example.assignment.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.ChatActivity
import com.example.assignment.R
import com.example.assignment.adapters.HistoryAdapter.RewardViewHolder
import com.example.assignment.utils.AppConstants
import com.example.assignment.utils.Botmodel.BotNames


class HistoryAdapter(private var context: Context, private var botNamesArrayList: ArrayList<BotNames>) :
    RecyclerView.Adapter<RewardViewHolder>() {

    var inflater: LayoutInflater? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardViewHolder {
        inflater = LayoutInflater.from(context)
        val view = inflater!!.inflate(R.layout.chathistory_item, parent, false)
        return RewardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RewardViewHolder, @SuppressLint("RecyclerView") i: Int) {
        holder.txtBotname.text = botNamesArrayList[i].name
        holder.txtMessage.text = botNamesArrayList[i].last_message
        holder.txtDate.text = botNamesArrayList[i].last_message_date
        holder.layoutRoot.setOnClickListener {

            var intent =Intent(context,ChatActivity::class.java)
            intent.putExtra(AppConstants.IntentKeys.BOT_NAME,botNamesArrayList[i].name)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return botNamesArrayList.size
    }

    inner class RewardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtBotname: TextView
        var txtMessage: TextView
        var txtDate: TextView
        var layoutRoot: LinearLayout

        init {
            txtBotname = itemView.findViewById(R.id.txtBotname)
            txtMessage = itemView.findViewById(R.id.txtMessage)
            txtDate = itemView.findViewById(R.id.txtDate)
            layoutRoot = itemView.findViewById(R.id.layoutRoot)

        }
    }
}