package com.adamcoding.prototype.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(

	@field:SerializedName("product_id")
	val productId: Int? = null,

	@field:SerializedName("imageurl")
	val imageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)
