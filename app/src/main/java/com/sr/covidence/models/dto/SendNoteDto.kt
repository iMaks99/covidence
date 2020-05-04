package com.sr.covidence.models.dto

import com.google.gson.annotations.SerializedName

data class SendNoteDto (
    @SerializedName("code") var code: Int,
    @SerializedName("status") var status: String,
    @SerializedName("data") var data: Obj
)

data class Obj(
    @SerializedName("id") var id: String,
    @SerializedName("dateCreation") var dateCreation: Long
)