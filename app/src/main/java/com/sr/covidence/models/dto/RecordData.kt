package com.sr.covidence.models.dto

import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

data class RecordData (
    @SerializedName("text") var text: String,
    @SerializedName("covidLikelihood") var covidLikelihood: String
)