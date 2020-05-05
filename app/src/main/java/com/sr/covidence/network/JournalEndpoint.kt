package com.sr.covidence.network

import com.sr.covidence.models.dto.GetDataForSendResponse
import com.sr.covidence.models.dto.JournalResponse
import com.sr.covidence.models.dto.RecordData
import com.sr.covidence.models.dto.SendNoteDto
import retrofit2.Call
import retrofit2.http.*

interface JournalEndpoint {

    @GET("/diary/find-records/all/")
    fun getAllNote(
        @Query("accessToken") accessToken: String,
        @Query("secretAccessToken") secretAccessToken: String,
        @Query("apiType") apiType: String
    ): Call<JournalResponse>


    @FormUrlEncoded
    @POST("/diary/make-record/")
    fun sendNote(
        @Field("accessToken") accessToken: String,
        @Field("secretAccessToken") secretAccessToken: String,
        @Field("apiType") apiType: String,
        @Field("text") text: String,
        @Field("covidLikelihood") covidLikelihood: String
    ): Call<SendNoteDto>

    @GET("/diary/find-records/all/")
    fun getDataForShare(
        @Query("accessToken") accessToken: String,
        @Query("secretAccessToken") secretAccessToken: String,
        @Query("apiType") apiType: String,
        @Query("email") email: String
    ): Call<GetDataForSendResponse>

}