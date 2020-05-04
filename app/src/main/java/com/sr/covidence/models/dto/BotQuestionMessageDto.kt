package com.sr.covidence.models.dto

import com.google.gson.annotations.SerializedName
import com.sr.covidence.models.MessageInterface
import com.sr.covidence.models.QuestionAnswer

data class BotQuestionMessageDto(
    @SerializedName("question") var question: String,
    @SerializedName("disabled") var isDisabled: Int,
    @SerializedName("code") var code: Long,
    @SerializedName("weight") var weight: Int,
    @SerializedName("answers") var answers: ArrayList<BotAnswerMessageDto>
) : QuestionAnswer(false), MessageInterface