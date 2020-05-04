package com.sr.covidence.models.dto

import com.google.gson.annotations.SerializedName
import com.sr.covidence.models.QuestionAnswer

data class BotAnswerMessageDto(
    @SerializedName("answer") var answerText: String,
    @SerializedName("value") var value: String,
    @SerializedName("cost") var cost: Int
) : QuestionAnswer(null)