package com.sr.covidence.models.dto

import com.google.gson.annotations.SerializedName
import com.sr.covidence.models.MessageInterface

data class BotAnswerMessageResponseDto(
    @SerializedName("code") var code: Int,
    @SerializedName("status") var status: String,
    @SerializedName("data") var data: TestResultDto
)

data class TestResultDto(
    @SerializedName("probability") var probability: Double
) : MessageInterface