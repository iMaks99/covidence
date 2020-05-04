package com.sr.covidence.chat_bot.view_holders

import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sr.covidence.R
import com.sr.covidence.models.dto.BotQuestionMessageDto
import kotlinx.android.synthetic.main.chat_bot_question_message.view.*


class ChatBotQuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(message: BotQuestionMessageDto) {
        itemView.chatBotQuestionMessageContent.movementMethod = LinkMovementMethod.getInstance()
        itemView.chatBotQuestionMessageContent.text = Html.fromHtml(message.question)

        itemView.chatBotQuestionMessageAnswerTrue.text = message.answers[0].answerText
        itemView.chatBotQuestionMessageAnswerFalse.text = message.answers[1].answerText

        if (message.isAnswered != null && message.isAnswered!!) {
            if (message.answers[0].isAnswered != null)
                itemView.chatBotQuestionMessageAnswerTrue.isSelected =
                    message.answers[0].isAnswered!!
            else if (message.answers[1].isAnswered != null)
                itemView.chatBotQuestionMessageAnswerFalse.isSelected =
                    message.answers[1].isAnswered!!

            itemView.chatBotQuestionMessageAnswerTrue.isEnabled = false
            itemView.chatBotQuestionMessageAnswerFalse.isEnabled = false
            itemView.chatBotQuestionMessageAnswerContainer.isEnabled = false
        } else {
            itemView.chatBotQuestionMessageAnswerTrue.setOnCheckedChangeListener { _, isChecked ->
                message.answers[0].isAnswered = isChecked
            }

            itemView.chatBotQuestionMessageAnswerFalse.setOnCheckedChangeListener { _, isChecked ->
                message.answers[1].isAnswered = isChecked
            }
        }

    }

    companion object {
        fun create(parent: ViewGroup): ChatBotQuestionViewHolder {
            return ChatBotQuestionViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.chat_bot_question_message, parent, false)
            )
        }
    }

}