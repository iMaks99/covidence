package com.sr.covidence.models.dto

import android.text.Html
import com.google.gson.annotations.SerializedName

data class GetDataForSendResponse(
    @SerializedName("code") var code: Int,
    @SerializedName("status") var status: String,
    @SerializedName("text") var text: String
)
