package com.adamcoding.prototype.data.remote.request

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
	@SerializedName("password")
	val password: String,

	@SerializedName("email")
	val email: String,

	@SerializedName("username")
	val username: String
)