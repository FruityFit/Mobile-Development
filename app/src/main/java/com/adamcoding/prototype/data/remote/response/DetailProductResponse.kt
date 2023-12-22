package com.adamcoding.prototype.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailProductResponse(

	@field:SerializedName("fruit")
	val fruit: String? = null,

	@field:SerializedName("product_id")
	val productId: Int? = null,

	@field:SerializedName("imageurl")
	val imageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("recipe")
	val recipe: String? = null,

	@field:SerializedName("description")
	val description: String? = null
)
