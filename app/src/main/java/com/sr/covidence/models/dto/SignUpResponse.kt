package com.sr.covidence.models.dto

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("code") var code: Int,
    @SerializedName("status") var status: String
)