package com.sr.covidence.network

import com.sr.covidence.models.dto.SignInResponse
import com.sr.covidence.models.dto.SignUpResponse
import retrofit2.Call
import retrofit2.http.*

interface RegistrationEndpoint {

    @FormUrlEncoded
    @POST("/user/signup/")
    fun signUp(
        @Field("userAgreement") userAgreement: Boolean,
        @Field("user") user: String,
        @Field("pass") pass: String,
        @Field("email") email: String,
        @Field("firstname") firstname: String,
        @Field("lastname") lastname: String,
        @Field("middlename") middlename: String,
        @Field("phone") phone: String
    ): Call<SignUpResponse>

    @FormUrlEncoded
    @GET("/user/signup/confirm/")
    fun sendCode(
        @Query("email") email: String,
        @Query("regKey") regKey: String
    ): Call<SignUpResponse>

    @FormUrlEncoded
    @POST("/user/login/")
    fun signIn(
        @Query("user") user: String,
        @Query("pass") pass: String,
        @Query("apiType") apiType: String = "mobile"
    ): Call<SignInResponse>
}