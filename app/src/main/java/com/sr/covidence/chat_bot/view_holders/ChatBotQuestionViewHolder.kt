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
import kotlin.random.Random


class ChatBotQuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(message: BotQuestionMessageDto) {

        val tag = System.currentTimeMillis()

        itemView.chatBotQuestionMessageAnswerContainer.tag = tag
        itemView.chatBotQuestionMessageAnswerTrue.tag = System.currentTimeMillis()
        itemView.chatBotQuestionMessageAnswerFalse.tag = System.currentTimeMillis()

        itemView.chatBotQuestionMessageContent.movementMethod = LinkMovementMethod.getInstance()
        itemView.chatBotQuestionMessageContent.text = Html.fromHtml(message.question)

        itemView.chatBotQuestionMessageAnswerContainer.clearCheck()

        itemView.chatBotQuestionMessageAnswerTrue.text = message.answers[0].answerText
        itemView.chatBotQuestionMessageAnswerFalse.text = message.answers[1].answerText

        if (message.answers[0].isAnswered != null)
            itemView.chatBotQuestionMessageAnswerTrue.isChecked =
                message.answers[0].isAnswered!!
        else if (message.answers[1].isAnswered != null)
            itemView.chatBotQuestionMessageAnswerFalse.isChecked =
                message.answers[1].isAnswered!!

        if (message.isAnswered != null && message.isAnswered!!) {
            itemView.chatBotQuestionMessageAnswerTrue.isEnabled = false
            itemView.chatBotQuestionMessageAnswerFalse.isEnabled = false
            itemView.chatBotQuestionMessageAnswerContainer.isEnabled = false
        } else {
            itemView.chatBotQuestionMessageAnswerTrue.isEnabled = true
            itemView.chatBotQuestionMessageAnswerFalse.isEnabled = true
            itemView.chatBotQuestionMessageAnswerContainer.isEnabled = true
        }

        itemView.chatBotQuestionMessageAnswerContainer.setOnCheckedChangeListener { group, checkedId ->
            if (group.tag == tag)
                when (checkedId) {
                    R.id.chatBotQuestionMessageAnswerTrue -> {
                        message.answers[0].isAnswered = true
                        message.answers[1].isAnswered = false
                    }
                    R.id.chatBotQuestionMessageAnswerFalse -> {
                        message.answers[0].isAnswered = false
                        message.answers[1].isAnswered = true
                    }
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