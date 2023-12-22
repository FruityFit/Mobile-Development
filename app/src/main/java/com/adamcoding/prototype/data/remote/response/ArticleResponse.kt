package com.adamcoding.prototype.data.remote.response

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("article_id") val articleId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("articleurl") val articleUrl: String,
    @SerializedName("imageurl") val imageUrl: String
)