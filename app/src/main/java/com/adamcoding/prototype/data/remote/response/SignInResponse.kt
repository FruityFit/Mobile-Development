package com.adamcoding.prototype.data.remote.response

import com.google.gson.annotations.SerializedName

data class SignInResponse(
	@SerializedName("message")
	val message: String,

	@SerializedName("token")
	val token: String
)