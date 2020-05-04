package com.sr.covidence.network

import com.sr.covidence.models.dto.NewsResponse
import retrofit2.Call
import retrofit2.http.GET

interface BrowseEndpoint {

    @GET("/news/list/allNews/")
    fun getAllNews(
    ): Call<NewsResponse>

}