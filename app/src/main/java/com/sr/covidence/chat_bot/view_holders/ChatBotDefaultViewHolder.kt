package com.sr.covidence.chat_bot.view_holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sr.covidence.R
import com.sr.covidence.models.MessageInterface
import com.sr.covidence.models.dto.TestResultDto
import com.sr.covidence.models.model.BotDefaultMessage
import kotlinx.android.synthetic.main.chat_bot_default_message.view.*

class ChatBotDefaultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(message: MessageInterface) {
        itemView.chatBotDefaultMessageContent.text = when (message) {
            is TestResultDto -> {
                "Вероятность заболевания ${message.probability.format(2)}"
            }
            is BotDefaultMessage -> message.message

            else -> ""
        }

    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)

    companion object {
        fun create(parent: ViewGroup): ChatBotDefaultViewHolder {
            return ChatBotDefaultViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.chat_bot_default_message, parent, false)
            )
        }
    }

}