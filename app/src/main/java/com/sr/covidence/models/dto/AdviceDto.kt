package com.sr.covidence.models.dto

import android.icu.text.CaseMap
import com.google.gson.annotations.SerializedName

data class AdviceDto(
    @SerializedName("id") var id: String,
    @SerializedName("uuid") var uuid: String,
    @SerializedName("title") var title: String,
    @SerializedName("excerpt") var excerpt: String,
    @SerializedName("html") var html: ArrayList<AdviceContent>
)

data class AdviceContent(
    @SerializedName("html") var html: String
)