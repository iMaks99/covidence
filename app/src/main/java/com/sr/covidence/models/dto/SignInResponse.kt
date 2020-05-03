package com.sr.covidence.models.dto

import com.google.gson.annotations.SerializedName

data class SignInResponse (
    @SerializedName("code") var code: Int,
    @SerializedName("status") var status: String,
    @SerializedName("accessToken") var accessToken: String,
    @SerializedName("secretAccessToken") var secretAccessToken: String

)