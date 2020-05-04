package com.sr.covidence.models.dto

import com.google.gson.annotations.SerializedName

data class GetUserResponse (
    @SerializedName("code") var code: Int,
    @SerializedName("status") var status: String,
    @SerializedName("data") var data: User
)

data class User(
    @SerializedName("id") var id: String,
    @SerializedName("user") var user: String,
    @SerializedName("email") var email: String,
    @SerializedName("firstname") var firstname: String,
    @SerializedName("middlename") var middlename: String,
    @SerializedName("lastname") var lastname: String,
    @SerializedName("address") var address: String,
    @SerializedName("covidLikelihood") var covidLikelihood: Float,
    @SerializedName("taxNumber") var taxNumber: String,
    @SerializedName("snilsNumber") var snilsNumber: String,
    @SerializedName("docType") var docType: String,
    @SerializedName("docNum") var docNum: String,
    @SerializedName("phone") var phone: String,
    @SerializedName("insPolicy") var insPolicy: String,
    @SerializedName("insPolicyNum") var insPolicyNum: String
    )