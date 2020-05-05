package com.sr.covidence.chat_bot

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.sr.covidence.R
import com.sr.covidence.models.MessageInterface
import com.sr.covidence.models.dto.BotAnswerMessageDto
import com.sr.covidence.models.dto.BotQuestionMessageDto
import com.sr.covidence.models.model.BotCommandMessage
import com.sr.covidence.models.model.BotDefaultMessage
import com.sr.covidence.models.model.UserMessage
import com.sr.covidence.models.requests.BotAnswerItemRequest
import com.sr.covidence.models.requests.BotAnswerRequest
import com.sr.covidence.utils.custom.OnCommandItemClickListener
import kotlinx.android.synthetic.main.fragment_chat_bot.*


class ChatBotFragment : Fragment(), OnCommandItemClickListener {

    private lateinit var pref: SharedPreferences
    private lateinit var botViewModel: ChatBotViewModel

    private val messageList = ArrayList<MessageInterface>()
    private lateinit var questions: ArrayList<BotQuestionMessageDto>

    private lateinit var chatBotAdapter: ChatBotAdapter
    private var isFirstLaunch = true
    private var isAnswered = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat_bot, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = context!!.getSharedPreferences("sharedPreferences", AppCompatActivity.MODE_PRIVATE)
        botViewModel = ViewModelProvider(this).get(ChatBotViewModel::class.java)

        chatBotAdapter = ChatBotAdapter(messageList, this)
        chatBotRecyclerView.adapter = chatBotAdapter

        isFirstLaunch = pref.getBoolean("isFirstLaunch", true)

        if (pref.getBoolean("isFirstLaunch", true)) {
            sendMessage(
                BotDefaultMessage("Привет, я твой помощник в борьбе с COVID-19, помогу тебе следить за твоим здоровьем во время самоизоляции, вызвать скорую помощь и заказать тест на дом."),
                false
            )

            sendMessage(
                BotDefaultMessage("Для полного функционала приложения (контакт с врачом и вызов скорой помощи) стоит зарегистрироваться, но и без этого ты сможешь меня использовать."),
                false
            )

            sendMessage(
                BotDefaultMessage("Так же без регистрации ты всегда сможешь пользоваться рядом моими функциями, в числе которых, быстрый тест на вероятность заболевания, новости, рекомендации, статистика, прогнозы на следующее несколько дней, так же ты сможешь вести дневник самоизоляции и получать ачивки за ее выполнение, обо всем подробнее сможешь узнать в разделах приложения, все интуитивно и понятно."),
                false
            )

            pref.edit().putBoolean("isFirstLaunch", false).apply()
        }

        botViewModel.getQuestionList().observe(viewLifecycleOwner, Observer {
            questions = it
        })


        chatBotEntrySend.setOnClickListener {
            if (chatBotEntryText.text.isNullOrBlank() || !isAnswered) return@setOnClickListener

            isAnswered = false

            if (!isFirstLaunch) {
                messageList.clear()
                chatBotAdapter.notifyDataSetChanged()
            }
            else
                isFirstLaunch = false

            sendMessage(
                UserMessage(
                    chatBotEntryText.text.toString()
                ), true
            )
            chatBotEntryText.text.clear()

            showTest()
        }
    }

    private fun sendMessage(message: MessageInterface, isScroll: Boolean) {
        messageList.add(message)
        chatBotAdapter.notifyItemInserted(messageList.size - 1)

        if (isScroll)
            chatBotRecyclerView.scrollToPosition(messageList.size - 1)
    }

    private fun showTest() {
        for (question in questions) {
            if (question.isDisabled == 1) continue

            val tempAnswers = ArrayList<BotAnswerMessageDto>()

            for (ans in question.answers)
                tempAnswers.add(BotAnswerMessageDto(ans.answerText, ans.value, ans.cost))

            val temp = BotQuestionMessageDto(
                question.question,
                question.isDisabled,
                question.code,
                question.weight,
                tempAnswers
            )
            sendMessage(temp, false)
        }
        sendMessage(BotCommandMessage("Отправить"), false)
    }

    override fun onItemClicked(position: Int) {

        val qSize = questions.filter { q -> q.isDisabled == 0 }.size

        val questionList = messageList.subList(position - qSize, position).toCollection(ArrayList())
        val answers = ArrayList<BotAnswerItemRequest>()

        questionList.forEach {
            if (it is BotQuestionMessageDto) {
                if (!it.answers.filter { ans -> ans.isAnswered == null }.isNullOrEmpty()) return
            }
        }

        questionList.forEach {
            if (it is BotQuestionMessageDto) {
                it.isAnswered = true
                val currentValue =
                    it.answers.filter { ans -> ans.isAnswered != null && ans.isAnswered!! }[0].value
                answers.add(BotAnswerItemRequest(it.code, currentValue))
            }
        }

        (messageList[position] as BotCommandMessage).isAnswered = true
        chatBotAdapter.notifyItemRangeChanged(position - qSize, qSize + 1, true)

        botViewModel.sendAnswerList(BotAnswerRequest(answers))
            .observe(viewLifecycleOwner, Observer {
                isAnswered = true
                sendMessage(it, true)

                when {
                    it.probability <= 3.5892857 -> {
                        sendMessage(
                            BotDefaultMessage("Поздравляю, у вас самая низкая вероятность заболевания, но вы все равно можете быть бессимптомным носителем вируса, будьте аккуратны"),
                            false
                        )
                        sendMessage(
                            BotDefaultMessage("Возможно вы феномен? Если есть желания успокоить себя пройдите тест на антитела к COVID-19"),
                            false
                        )
                        sendMessage(
                            BotDefaultMessage(
                                "Тест на антитела к COVID-19 (тест на иммунитет к вирусу) от «Клиники Рссвет» (4800 рублей)\nhttps://klinikarassvet.ru/patients/articles/testirovanie-na-antitela-k-covid-19/"
                            ), false
                        )
                        sendMessage(
                            BotDefaultMessage(
                                "Тест на Антитела к COVID-19 от «ChaikaHealth» (4800 реублей)\nhttps://chaika.com/covid19-antibodies"
                            ), false
                        )
                    }
                    it.probability <= 6.58214281428571 -> {
                        sendMessage(
                            BotDefaultMessage(
                                "Вероятность вашего заражения низкая, но вы все равно можете быть носителем вируса, будьте аккуратны"
                            ), false
                        )
                    }
                    it.probability <= 15.628571357142839 -> {
                        sendMessage(
                            BotDefaultMessage(
                                "У вас есть несколько симптомов COVID-19, но не стоит волновать, вероятность все же низкая."
                            ), false
                        )
                    }
                    it.probability <= 30.72857132857139 -> {
                        sendMessage(
                            BotDefaultMessage(
                                "У вас есть несколько симптомов COVID-19, я бы порекомендовал вам записаться на тест онлайн у наших партнеров."
                            ), false
                        )
                        sendMessage(
                            BotDefaultMessage(
                                "Гемотест (3000 рублей)\nhttps://www.labquest.ru/covid19/"
                            ), false
                        )
                        sendMessage(
                            BotDefaultMessage(
                                "LabQuest (5395 рублей)\nhttps://www.gemotest.ru/catalog/po-laboratornym-napravleniyam/diagnostika-infektsiy/ptsr-diagnostika/individualnaya-diagnostika-vozbuditeley/koronavirus_sars_cov_2_mazok_kach/"
                            ), false
                        )
                        sendMessage(
                            BotDefaultMessage(
                                "Бесплатный тест на COVID-19 на дому при поддержки Яндекса\nhttps://help.yandex.ru/covid19-test"
                            ), false
                        )
                    }
                    it.probability <= 51.88214272857136 -> {
                        sendMessage(
                            BotDefaultMessage(
                                "Более трех симптомов подходят под симптомы COVID-19, я рекомендую сделать тест и не в коем случае не выходить из дома!"
                            ), false
                        )
                        sendMessage(
                            BotDefaultMessage(
                                "Гемотест (3000 рублей)\nhttps://www.labquest.ru/covid19/"
                            ), false
                        )
                        sendMessage(
                            BotDefaultMessage(
                                "LabQuest (5395 рублей)\nhttps://www.gemotest.ru/catalog/po-laboratornym-napravleniyam/diagnostika-infektsiy/ptsr-diagnostika/individualnaya-diagnostika-vozbuditeley/koronavirus_sars_cov_2_mazok_kach/ \n"
                            ), false
                        )
                        sendMessage(
                            BotDefaultMessage(
                                "Бесплатный тест на COVID-19 на дому при поддержки Яндекса\nhttps://help.yandex.ru/covid19-test"
                            ), false
                        )
                    }
                    it.probability <= 79.08928555714276 -> {
                        sendMessage(
                            BotDefaultMessage(
                                "Это самая большая возможная вероятность моего теста, настоятельно рекомендую сдать анализы на COVID-19 и при плохом самочувствие вызвать скорую помощь."
                            ), false
                        )
                        sendMessage(
                            BotDefaultMessage(
                                "Тесты наших партнеров:"
                            ), false
                        )
                        sendMessage(
                            BotDefaultMessage(
                                "Гемотест (3000 рублей)\nhttps://www.labquest.ru/covid19/"
                            ), false
                        )
                        sendMessage(
                            BotDefaultMessage(
                                "LabQuest (5395 рублей)\nhttps://www.gemotest.ru/catalog/po-laboratornym-napravleniyam/diagnostika-infektsiy/ptsr-diagnostika/individualnaya-diagnostika-vozbuditeley/koronavirus_sars_cov_2_mazok_kach/ \n"
                            ), false
                        )
                        sendMessage(
                            BotDefaultMessage(
                                "Бесплатный тест на COVID-19 на дому при поддержки Яндекса\nhttps://help.yandex.ru/covid19-test"
                            ), false
                        )
                    }
                }
            })
    }

}
