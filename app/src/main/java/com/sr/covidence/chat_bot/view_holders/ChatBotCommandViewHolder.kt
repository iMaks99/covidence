package com.sr.covidence.chat_bot.view_holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sr.covidence.R
import com.sr.covidence.models.model.BotCommandMessage
import com.sr.covidence.utils.custom.OnCommandItemClickListener
import kotlinx.android.synthetic.main.chat_bot_command_message.view.*
import java.text.FieldPosition

class ChatBotCommandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(message: BotCommandMessage, itemClickListener: OnCommandItemClickListener, position: Int) {

        if (message.isAnswered != null && !message.isAnswered!!) {
            itemView.chatBotCommandMessageContent.text = message.messageText

            itemView.chatBotCommandMessageContent.setOnClickListener {
                itemClickListener.onItemClicked(position)
            }
        } else {
            itemView.chatBotCommandMessageContent.text = "Вы уже ответили"
            itemView.chatBotCommandMessageContent.isEnabled = false
        }
    }

    companion object {
        fun create(parent: ViewGroup): ChatBotCommandViewHolder {
            return ChatBotCommandViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.chat_bot_command_message, parent, false)
            )
        }
    }

}