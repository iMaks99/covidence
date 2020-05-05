package com.sr.covidence.network

import com.sr.covidence.models.dto.AdviceListResponseDto
import com.sr.covidence.models.dto.BotQuestionMessageResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AdviceEndpoint {

    @GET("/recommendation/general/")
    fun getAdviceGeneral(
    ): Call<AdviceListResponseDto>

    @GET("/recommendation/:regionCode={regionCode}/")
    fun getAdviceByRegion(
        @Path("regionCode") code: Int
    ): Call<AdviceListResponseDto>

}