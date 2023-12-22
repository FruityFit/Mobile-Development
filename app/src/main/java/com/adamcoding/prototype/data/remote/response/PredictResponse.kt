package com.adamcoding.prototype.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PredictResponse(

	@field:SerializedName("sodium_mg")
	val sodiumMg: String? = null,

	@field:SerializedName("cholesterol_mg")
	val cholesterolMg: String? = null,

	@field:SerializedName("confidence")
	val confidence: Double? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("fiber_g")
	val fiberG: String? = null,

	@field:SerializedName("calories")
	val calories: String? = null,

	@field:SerializedName("fat_saturated_g")
	val fatSaturatedG: String? = null,

	@field:SerializedName("sugar_g")
	val sugarG: String? = null,

	@field:SerializedName("fat_total_g")
	val fatTotalG: String? = null,

	@field:SerializedName("protein_g")
	val proteinG: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("serving_size_g")
	val servingSizeG: String? = null,

	@field:SerializedName("carbohydrates_total_g")
	val carbohydratesTotalG: String? = null,

	@field:SerializedName("potassium_mg")
	val potassiumMg: String? = null
) : Parcelable
