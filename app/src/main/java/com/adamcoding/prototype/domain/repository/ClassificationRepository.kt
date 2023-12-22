package com.adamcoding.prototype.domain.repository

import androidx.lifecycle.LiveData
import com.adamcoding.prototype.data.remote.response.DetailProductResponse
import com.adamcoding.prototype.data.remote.response.PredictResponse
import com.adamcoding.prototype.data.remote.response.ProductResponse
import com.adamcoding.prototype.util.Result
import java.io.File

interface ClassificationRepository {
    fun predict(imageFile: File): LiveData<Result<PredictResponse>>
    fun getProducts(fruitName: String): LiveData<Result<List<ProductResponse>>>
    fun getProductById(id: Int): LiveData<Result<DetailProductResponse>>
}