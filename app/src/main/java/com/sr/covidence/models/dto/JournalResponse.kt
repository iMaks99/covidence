package com.sr.covidence.models.dto

import com.google.gson.annotations.SerializedName

data class JournalResponse(
    @SerializedName("code") var code: Int,
    @SerializedName("status") var status: String,
    @SerializedName("data") var data: ArrayList<Note>
)

data class Note(
    @SerializedName("dateCreation") var dataCreation: Long,
    @SerializedName("dateModification") var dateModification: Long,
    @SerializedName("_id") var id: String,
    @SerializedName("text") var recordData: String
)
