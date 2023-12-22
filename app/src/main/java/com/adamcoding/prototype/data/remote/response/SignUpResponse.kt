package com.adamcoding.prototype.data.remote.response

import com.google.gson.annotations.SerializedName

data class SignUpResponse(

    @field:SerializedName("message")
    val message: String
)