package com.sr.covidence.network

import com.sr.covidence.models.dto.GetUserResponse
import com.sr.covidence.models.dto.JournalResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileEndpoint {

    @GET("/user/info/")
    fun getUser(
        @Query("accessToken") accessToken: String,
        @Query("secretAccessToken") secretAccessToken: String,
        @Query("apiType") apiType: String
    ): Call<GetUserResponse>


}