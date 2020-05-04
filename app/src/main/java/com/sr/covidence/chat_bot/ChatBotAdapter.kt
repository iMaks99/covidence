package com.sr.covidence.chat_bot

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sr.covidence.chat_bot.view_holders.ChatBotCommandViewHolder
import com.sr.covidence.chat_bot.view_holders.ChatBotDefaultViewHolder
import com.sr.covidence.chat_bot.view_holders.ChatBotQuestionViewHolder
import com.sr.covidence.chat_bot.view_holders.ChatBotUserViewHolder
import com.sr.covidence.models.MessageInterface
import com.sr.covidence.models.dto.BotQuestionMessageDto
import com.sr.covidence.models.dto.TestResultDto
import com.sr.covidence.models.model.UserMessage
import com.sr.covidence.models.enums.ChatViewType
import com.sr.covidence.models.model.BotCommandMessage
import com.sr.covidence.utils.custom.OnCommandItemClickListener
import com.sr.covidence.utils.dpToPx
import kotlinx.android.synthetic.main.chat_bot_command_message.view.*
import kotlinx.android.synthetic.main.chat_bot_question_message.view.*

class ChatBotAdapter(
    private var chatList: ArrayList<MessageInterface>,
    var itemClickListener: OnCommandItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ChatViewType.USER.viewType -> ChatBotUserViewHolder.create(parent)
            ChatViewType.QUESTION.viewType -> ChatBotQuestionViewHolder.create(parent)
            ChatViewType.COMMAND.viewType -> ChatBotCommandViewHolder.create(parent)
            ChatViewType.DEFAULT.viewType -> ChatBotDefaultViewHolder.create(parent)
            else -> TODO()
        }

    override fun getItemCount(): Int = chatList.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            when (payloads[0]) {
                true -> {

                    when (holder) {
                        is ChatBotQuestionViewHolder -> {
                            holder.itemView.chatBotQuestionMessageAnswerTrue.isEnabled = false
                            holder.itemView.chatBotQuestionMessageAnswerFalse.isEnabled = false
                            holder.itemView.chatBotQuestionMessageAnswerContainer.isEnabled = false
                        }

                        is ChatBotCommandViewHolder -> {
                            holder.itemView.chatBotCommandMessageContent.text = "Вы уже ответили"
                            holder.itemView.chatBotCommandMessageContent.isEnabled = false
                        }
                    }
                }
                false -> {

                }
            }
        } else
            super.onBindViewHolder(holder, position, payloads)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (position == 0) {
            val params =
                holder.itemView.layoutParams as RecyclerView.LayoutParams
            params.topMargin =
                dpToPx(holder.itemView.context, 8f)
            holder.itemView.layoutParams = params
        } else {
            val params =
                holder.itemView.layoutParams as RecyclerView.LayoutParams
            params.topMargin =
                dpToPx(holder.itemView.context, 0f)
            holder.itemView.layoutParams = params
        }

        when (getItemViewType(position)) {
            ChatViewType.USER.viewType -> (holder as ChatBotUserViewHolder).bind(chatList[position] as UserMessage)
            ChatViewType.QUESTION.viewType -> (holder as ChatBotQuestionViewHolder).bind(chatList[position] as BotQuestionMessageDto)
            ChatViewType.DEFAULT.viewType -> (holder as ChatBotDefaultViewHolder).bind(chatList[position])
            ChatViewType.COMMAND.viewType -> (holder as ChatBotCommandViewHolder).bind(
                chatList[position] as BotCommandMessage,
                itemClickListener,
                position
            )
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (chatList[position]) {
            is UserMessage -> ChatViewType.USER.viewType
            is BotQuestionMessageDto -> ChatViewType.QUESTION.viewType
            is BotCommandMessage -> ChatViewType.COMMAND.viewType
            is TestResultDto -> ChatViewType.DEFAULT.viewType
            else -> TODO()
        }
}