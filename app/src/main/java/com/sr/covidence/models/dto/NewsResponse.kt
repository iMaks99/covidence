package com.sr.covidence.models.dto

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("code") var code: Int,
    @SerializedName("status") var status: String,
    @SerializedName("data") var data: Post
)

data class Post(
    @SerializedName("posts") var posts: ArrayList<News>
)

data class News(
    @SerializedName("title") var title: String,
    @SerializedName("html") var html: List<HtmlPart>?,
    @SerializedName("tags") var tags: ArrayList<String>
)

data class HtmlPart(
    @SerializedName("img") var img: String?,
    @SerializedName("html") var html: String?
)

