package com.example.assignment

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.adapters.HistoryAdapter
import com.example.assignment.application.SharedPreferenceUtils
import com.example.assignment.utils.AppConstants
import com.example.assignment.utils.Botmodel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.apache.commons.lang3.StringUtils
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    var botmodel: Botmodel? = null
    var chatdata: String? = ""
    var botNamesArrayList: ArrayList<Botmodel.BotNames>? = null
    var historyAdapter: HistoryAdapter? = null
    var context: Context? = null
    var rlvChatHistory: RecyclerView? = null
    var addBot: FloatingActionButton? = null
    var sdf:SimpleDateFormat?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this@MainActivity
        rlvChatHistory = findViewById(R.id.rlvChatHistory)
        addBot = findViewById(R.id.addBot)
        sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        botNamesArrayList = ArrayList<Botmodel.BotNames>()


        historyAdapter = HistoryAdapter(this@MainActivity, botNamesArrayList!!)
        rlvChatHistory!!.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rlvChatHistory!!.adapter = historyAdapter
        refreshdata()

        addBot!!.setOnClickListener {
            addBotDialog(getString(R.string.add_bot))
        }
    }

    override fun onResume() {
        super.onResume()
        refreshdata()
    }

    fun refreshdata() {
        chatdata =
            SharedPreferenceUtils.preferenceGetString(AppConstants.SharedPreferenceKeys.CHAT_DATA)

        if(botNamesArrayList!=null && botNamesArrayList!!.size>0){
            botNamesArrayList!!.clear()
        }

        if (StringUtils.isNoneEmpty(chatdata)) {
            botmodel = Gson().fromJson(chatdata, Botmodel::class.java)
//            botNamesArrayList!!.addAll(newbotmodel!!.botNames)

            botNamesArrayList!!.addAll(
                Gson().fromJson(
                    Gson().toJson(botmodel!!.botNames),
                    object : TypeToken<List<Botmodel.BotNames?>?>() {}.type
                )
            )
        } else {
            botmodel = Botmodel(ArrayList<Botmodel.BotNames>())
        }
        if(historyAdapter!=null){
            if(botNamesArrayList!!.size>0){
                botNamesArrayList!!.sortWith(Comparator { o1, o2 ->
                    val x1: Date = sdf!!.parse(o1.last_message_date)
                    val x2: Date = sdf!!.parse(o2.last_message_date)
                    x2.compareTo(x1)
                })
            }
            historyAdapter?.notifyDataSetChanged()
        }
    }

    private fun addBotDialog(title: String) {
        val alert = AlertDialog.Builder(this@MainActivity)

        val edittext = EditText(this@MainActivity)
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        edittext.setLayoutParams(lp)
        edittext.hint = getString(R.string.enter_name)
        edittext.maxLines = 1

        var layout = this@MainActivity?.let { RelativeLayout(it) }
        layout?.setPadding(45, 15, 45, 0)
        alert.setTitle(title)
        layout?.addView(edittext)
        alert.setView(layout)

        alert.setPositiveButton(
            getString(R.string.add),
            DialogInterface.OnClickListener { dialog, which ->
                run {
                    val qName = edittext.text.toString()

//                    for (item in botNamesArrayList!!) {
//                        if (item.name.lowercase().trim().equals(qName.lowercase().trim())) {
//                            return@OnClickListener
//                        }
//                    }
                    val currentCal = Calendar.getInstance()

                    var botName = Botmodel.BotNames(
                        qName,
                        sdf!!.format(currentCal.getTime()),
                        getString(R.string.nomessage),
                        ArrayList<Botmodel.BotNames.Messages>()
                    )

                    botNamesArrayList!!.add(botName)


                    botNamesArrayList!!.sortWith(Comparator { o1, o2 ->
                        val x1: Date = sdf!!.parse(o1.last_message_date)
                        val x2: Date = sdf!!.parse(o2.last_message_date)
                        x2.compareTo(x1)
                    })

                    if(botmodel?.botNames!=null && botmodel?.botNames!!.size>0) {
                        botmodel?.botNames?.clear()
                    }


                    botmodel?.botNames?.addAll(botNamesArrayList!!)

                    SharedPreferenceUtils.preferencePutString(
                        AppConstants.SharedPreferenceKeys.CHAT_DATA,
                        Gson().toJson(botmodel)
                    )
                    historyAdapter?.notifyDataSetChanged()


//                    showToast("Posted to leaderboard successfully")
//                    view?.hideKeyboard()
                }
            })

        alert.setNegativeButton(
            getString(R.string.cancel),
            DialogInterface.OnClickListener { dialog, which ->
                run {
                    dialog.dismiss()
                }
            })

        alert.show()
    }


}