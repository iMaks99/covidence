package com.sr.covidence.models.dto

import com.google.gson.annotations.SerializedName

data class BotQuestionMessageResponseDto(
    @SerializedName("code") var code: Int,
    @SerializedName("status") var status: String,
    @SerializedName("data") var data: QuestionResponseDto
)

data class QuestionResponseDto(
    @SerializedName("questions") var questions: ArrayList<BotQuestionMessageDto>
)
