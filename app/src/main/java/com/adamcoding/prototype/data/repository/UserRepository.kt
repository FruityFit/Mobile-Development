package com.adamcoding.prototype.data.repository

import com.adamcoding.prototype.data.remote.request.SignUpRequest
import com.adamcoding.prototype.data.remote.response.SignUpResponse
import com.adamcoding.prototype.data.remote.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val apiService: ApiService) {

    fun signUp(username: String, email: String, password: String, callback: (Boolean, String?) -> Unit) {
        val signUpRequest = SignUpRequest(password, email, username)

        apiService.signUp(signUpRequest).enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                if (response.isSuccessful) {
                    callback(true, response.body()?.message)
                } else {
                    callback(false, "Username atau email sudah ada")
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                callback(false, t.message)
            }
        })
    }
}