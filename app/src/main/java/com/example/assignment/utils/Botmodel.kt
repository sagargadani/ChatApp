package com.example.assignment.utils

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Botmodel(
    @Expose
    @SerializedName("BotNames")
    var botNames: ArrayList<BotNames>
) {
    data class BotNames(
        @Expose
        @SerializedName("name")
        val name: String,
        @Expose
        @SerializedName("last_message_date")
        var last_message_date: String,
        @Expose
        @SerializedName("last_message")
        var last_message: String,
        @Expose
        @SerializedName("messages")
        var messages: ArrayList<Messages>


    ) {
        data class Messages(
            @Expose
            @SerializedName("sender")
            val sender: String,
            @Expose
            @SerializedName("message")
            val message: String,
            @Expose
            @SerializedName("messdate")
            val messdate: String
        )
    }

}



