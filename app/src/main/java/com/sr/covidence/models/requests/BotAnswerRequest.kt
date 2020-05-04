package com.sr.covidence.models.requests

import com.google.gson.annotations.SerializedName
import com.sr.covidence.models.dto.BotAnswerMessageDto

data class BotAnswerRequest(
    @SerializedName("answers") var answers: ArrayList<BotAnswerItemRequest>
)

data class BotAnswerItemRequest(
    @SerializedName("code") var code: Long ,
    @SerializedName("value") var value: String
)