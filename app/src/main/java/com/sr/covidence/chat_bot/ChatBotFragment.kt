package com.sr.covidence.chat_bot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.sr.covidence.R
import com.sr.covidence.models.MessageInterface
import com.sr.covidence.models.dto.BotAnswerMessageDto
import com.sr.covidence.models.dto.BotQuestionMessageDto
import com.sr.covidence.models.model.BotCommandMessage
import com.sr.covidence.models.model.UserMessage
import com.sr.covidence.models.requests.BotAnswerItemRequest
import com.sr.covidence.models.requests.BotAnswerRequest
import com.sr.covidence.utils.custom.OnCommandItemClickListener
import kotlinx.android.synthetic.main.fragment_chat_bot.*


class ChatBotFragment : Fragment(), OnCommandItemClickListener {

    private lateinit var botViewModel: ChatBotViewModel

    private val messageList = ArrayList<MessageInterface>()
    private lateinit var questions: ArrayList<BotQuestionMessageDto>

    private lateinit var chatBotAdapter: ChatBotAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat_bot, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        botViewModel = ViewModelProvider(this).get(ChatBotViewModel::class.java)

        botViewModel.getQuestionList().observe(viewLifecycleOwner, Observer {
            questions = it

            for (question in questions)
                sendMessage(question, false)
            sendMessage(BotCommandMessage("Отправить"), false)

        })

        chatBotAdapter = ChatBotAdapter(messageList, this)
        chatBotRecyclerView.adapter = chatBotAdapter

        chatBotEntrySend.setOnClickListener {
            if (chatBotEntryText.text.isNullOrBlank()) return@setOnClickListener

            sendMessage(
                UserMessage(
                    chatBotEntryText.text.toString()
                ), true
            )
            chatBotEntryText.text.clear()
        }
    }

    private fun sendMessage(message: MessageInterface, isScroll: Boolean) {
        messageList.add(message)
        chatBotAdapter.notifyItemInserted(messageList.size - 1)

        if (isScroll)
            chatBotRecyclerView.scrollToPosition(messageList.size - 1)
    }

    override fun onItemClicked(position: Int) {
        val questionList = messageList.subList(position - 5, position).toCollection(ArrayList())
        val answers = ArrayList<BotAnswerItemRequest>()

        questionList.forEach {
            if (it is BotQuestionMessageDto) {
                it.isAnswered = true
                val currentValue =
                    it.answers.filter { ans -> ans.isAnswered != null && ans.isAnswered!! }[0].value
                answers.add(BotAnswerItemRequest(it.code, currentValue))
            }
        }

        (messageList[position] as BotCommandMessage).isAnswered = true
        chatBotAdapter.notifyItemRangeChanged(position - 5, 6, true)


        botViewModel.sendAnswerList(BotAnswerRequest(answers))
            .observe(viewLifecycleOwner, Observer {
                sendMessage(it, true)
            })
    }

}
