package com.sr.covidence.chat_bot.view_holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sr.covidence.R
import com.sr.covidence.models.model.UserMessage
import kotlinx.android.synthetic.main.chat_bot_user_message.view.*

class ChatBotUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(message: UserMessage) {
        itemView.chatBotUserMessageContent.text = message.messageText
    }

    companion object {
        fun create(parent: ViewGroup): ChatBotUserViewHolder {
            return ChatBotUserViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.chat_bot_user_message, parent, false)
            )
        }
    }

}