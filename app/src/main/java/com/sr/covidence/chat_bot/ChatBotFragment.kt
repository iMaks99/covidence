package com.sr.covidence.chat_bot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sr.covidence.R
import com.sr.covidence.models.MessageInterface
import com.sr.covidence.models.dto.UserMessageDto
import kotlinx.android.synthetic.main.fragment_chat_bot.*


class ChatBotFragment : Fragment() {

    private val messageList = ArrayList<MessageInterface>()
    private lateinit var chatBotAdapter: ChatBotAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat_bot, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatBotAdapter = ChatBotAdapter(messageList)
        chatBotRecyclerView.adapter = chatBotAdapter

        chatBotEntrySend.setOnClickListener {
            if (chatBotEntryText.text.isNullOrBlank()) return@setOnClickListener

            sendMessage(UserMessageDto(chatBotEntryText.text.toString()))
            chatBotEntryText.text.clear()
        }
    }

    private fun sendMessage(message: MessageInterface) {
        messageList.add(message)
        chatBotAdapter.notifyItemInserted(messageList.size - 1)
    }

}
