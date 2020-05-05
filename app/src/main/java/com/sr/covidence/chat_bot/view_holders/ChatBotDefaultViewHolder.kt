package com.sr.covidence.chat_bot.view_holders

import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
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
            is BotDefaultMessage -> {

                val formattedDescription = message.message.split(" ").toTypedArray()

                for (i in formattedDescription.indices)
                    when {
                        formattedDescription[i].contains("\r\n") -> {
                            val temp = formattedDescription[i].split("\r\n").toTypedArray()
                            for (j in temp.indices)
                                if (temp[j].contains("http"))
                                    temp[j] = "<a href='${temp[j]}'>${temp[j]}</a>"

                            formattedDescription[i] = temp.joinToString("<br/>")
                        }
                        formattedDescription[i].contains("\n") -> {
                            val temp = formattedDescription[i].split("\n").toTypedArray()
                            for (j in temp.indices)
                                if (temp[j].contains("http"))
                                    temp[j] = "<a href='${temp[j]}'>${temp[j]}</a>"

                            formattedDescription[i] = temp.joinToString("<br/>")
                        }
                        formattedDescription[i].contains("http") -> formattedDescription[i] =
                            "<a href='${formattedDescription[i]}'>${formattedDescription[i]}</a>"
                    }

                HtmlCompat.fromHtml(
                    formattedDescription.joinToString(" "),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
            }

            else -> ""
        }

        itemView.chatBotDefaultMessageContent.movementMethod = LinkMovementMethod.getInstance()

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