package com.sr.covidence.chat_bot

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sr.covidence.chat_bot.view_holders.ChatBotUserViewHolder
import com.sr.covidence.models.MessageInterface
import com.sr.covidence.models.dto.UserMessageDto
import com.sr.covidence.models.enums.ChatViewType

class ChatBotAdapter (private var chatList: ArrayList<MessageInterface>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ChatViewType.USER.viewType -> ChatBotUserViewHolder.create(parent)
            else -> TODO()
        }

    override fun getItemCount(): Int = chatList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ChatViewType.USER.viewType -> (holder as ChatBotUserViewHolder).bind(chatList[position] as UserMessageDto)
//            ChatViewType.BOT.viewType -> {
//                (holder as ChatBotMessageViewHolder).bind(chatList[position])
//                if (position == itemCount - 1) {
//                    val params =
//                        holder.itemView.layoutParams as RecyclerView.LayoutParams
//                    params.bottomMargin =
//                        dpToPixel(holder.itemView.context, 10f)
//                    holder.itemView.layoutParams = params
//                } else {
//                    val params =
//                        holder.itemView.layoutParams as RecyclerView.LayoutParams
//                    params.bottomMargin =
//                        dpToPixel(holder.itemView.context, 0f)
//                    holder.itemView.layoutParams = params
//                }
//            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (chatList[position]) {
            is UserMessageDto -> ChatViewType.USER.viewType
            else -> TODO()
        }
}