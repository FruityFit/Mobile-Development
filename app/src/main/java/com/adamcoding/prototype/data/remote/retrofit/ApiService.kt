package com.adamcoding.prototype.data.remote.retrofit

import com.adamcoding.prototype.data.remote.request.SignInRequest
import com.adamcoding.prototype.data.remote.request.SignUpRequest
import com.adamcoding.prototype.data.remote.response.ArticleResponse
import com.adamcoding.prototype.data.remote.response.DetailProductResponse
import com.adamcoding.prototype.data.remote.response.ProductResponse
import com.adamcoding.prototype.data.remote.response.SignInResponse
import com.adamcoding.prototype.data.remote.response.SignUpResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    /**
    Dokumentasi : https://documenter.getpostman.com/view/23146615/2s9YeHZAPM
     */
    @POST("user/signup")
    fun signUp(@Body signUpRequest: SignUpRequest): Call<SignUpResponse>

    @POST("user/signin")
    fun signIn(@Body signInRequest: SignInRequest): Call<SignInResponse>

    @GET("products")
    suspend fun getProducts(): List<ProductResponse>

    @GET("products/rec/{name}")
    suspend fun getProductsByFruit(
        @Path("name") fruitName: String
    ): List<ProductResponse>

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): DetailProductResponse

    @GET("articles")
    suspend fun getArticles(): Response<List<ArticleResponse>>
}