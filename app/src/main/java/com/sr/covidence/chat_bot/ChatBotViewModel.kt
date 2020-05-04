package com.sr.covidence.chat_bot

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sr.covidence.models.dto.BotAnswerMessageResponseDto
import com.sr.covidence.models.dto.BotQuestionMessageDto
import com.sr.covidence.models.dto.BotQuestionMessageResponseDto
import com.sr.covidence.models.dto.TestResultDto
import com.sr.covidence.models.requests.BotAnswerRequest
import com.sr.covidence.network.NetworkService
import io.reactivex.schedulers.TestScheduler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatBotViewModel : ViewModel() {

    private val mBotRepository = NetworkService.instance!!.botEndpoint

    private var questionsList = MutableLiveData<ArrayList<BotQuestionMessageDto>>()
    private var testResult = MutableLiveData<TestResultDto>()

    fun getQuestionList(): LiveData<ArrayList<BotQuestionMessageDto>> {
        mBotRepository!!.getBotQuestions()
            .enqueue(object : Callback<BotQuestionMessageResponseDto> {
                override fun onFailure(call: Call<BotQuestionMessageResponseDto>, t: Throwable) {
                    Log.w(this::class.java.name, t.message.toString())
                }

                override fun onResponse(
                    call: Call<BotQuestionMessageResponseDto>,
                    response: Response<BotQuestionMessageResponseDto>
                ) {
                    if (response.isSuccessful) {
                        questionsList.postValue(response.body()!!.data.questions)
                    } else
                        Log.w(this::class.java.name, "Error in bot questions list request")

                }
            })

        return questionsList
    }

    fun sendAnswerList(answer: BotAnswerRequest): LiveData<TestResultDto> {
        mBotRepository!!.sendBotAnswers(answer)
            .enqueue(object : Callback<BotAnswerMessageResponseDto> {
                override fun onFailure(call: Call<BotAnswerMessageResponseDto>, t: Throwable) {
                    Log.w(this::class.java.name, t.message.toString())
                }

                override fun onResponse(
                    call: Call<BotAnswerMessageResponseDto>,
                    response: Response<BotAnswerMessageResponseDto>
                ) {
                    if (response.isSuccessful) {
                        testResult.postValue(response.body()!!.data)
                    } else
                        Log.w(this::class.java.name, "Error in bot test result request")
                }

            })

        return testResult
    }

}