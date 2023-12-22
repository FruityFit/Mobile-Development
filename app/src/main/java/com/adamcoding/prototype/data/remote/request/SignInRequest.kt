package com.adamcoding.prototype.data.remote.request

import com.google.gson.annotations.SerializedName

data class SignInRequest(
	@SerializedName("identifier")
	val identifier: String,

	@SerializedName("password")
	val password: String
)