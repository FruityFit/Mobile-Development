package com.adamcoding.prototype.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.adamcoding.prototype.data.remote.response.DetailProductResponse
import com.adamcoding.prototype.data.remote.response.PredictResponse
import com.adamcoding.prototype.data.remote.response.ProductResponse
import com.adamcoding.prototype.data.remote.retrofit.ApiService
import com.adamcoding.prototype.data.remote.retrofit.PredictService
import com.adamcoding.prototype.domain.repository.ClassificationRepository
import com.adamcoding.prototype.util.Result
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClassificationRepositoryImpl @Inject constructor(
    private val predictService: PredictService,
    private val apiService: ApiService,
) : ClassificationRepository {

    override fun predict(imageFile: File): LiveData<Result<PredictResponse>> = liveData {
        emit(Result.Loading())
        try {
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart = MultipartBody.Part.createFormData(
                "file",
                imageFile.name,
                requestImageFile
            )
            val result = predictService.predict(imageMultipart)
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    override fun getProducts(fruitName: String): LiveData<Result<List<ProductResponse>>> = liveData {
        emit(Result.Loading())
        try {
            val result = apiService.getProductsByFruit(fruitName)
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }

    override fun getProductById(id: Int): LiveData<Result<DetailProductResponse>> = liveData {
        emit(Result.Loading())
        try {
            val result = apiService.getProductById(id)
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }
}