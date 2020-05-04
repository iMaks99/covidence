package com.sr.covidence.network

import com.sr.covidence.models.dto.BotAnswerMessageResponseDto
import com.sr.covidence.models.dto.BotQuestionMessageResponseDto
import com.sr.covidence.models.requests.BotAnswerRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BotEndpoint {

    @GET("/bot/questions/")
    fun getBotQuestions(
    ): Call<BotQuestionMessageResponseDto>

    @POST("/bot/answers/")
    fun sendBotAnswers(
        @Body answers: BotAnswerRequest
    ): Call<BotAnswerMessageResponseDto>

}