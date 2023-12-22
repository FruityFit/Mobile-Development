package com.adamcoding.prototype.ui.fruit_result

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.adamcoding.prototype.data.remote.response.PredictResponse
import com.adamcoding.prototype.domain.repository.ClassificationRepository
import com.adamcoding.prototype.ui.fruit_result.model.FruitNutrition
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FruitResultViewModel @Inject constructor(
    private val classificationRepository: ClassificationRepository,
) : ViewModel() {

    private var fruitNutrition = mutableListOf<FruitNutrition>()

    fun getProducts(fruitName: String) = classificationRepository.getProducts(fruitName)

    fun addNutrition(result: PredictResponse) {
        with(fruitNutrition) {
            add(FruitNutrition("Calories", result.calories))
            add(FruitNutrition("Carbohydrate", result.carbohydratesTotalG))
            add(FruitNutrition("Cholestrol", result.cholesterolMg))
            add(FruitNutrition("Fat Saturated", result.fatSaturatedG))
            add(FruitNutrition("Fat Total", result.fatTotalG))
            add(FruitNutrition("Fiber", result.fiberG))
            add(FruitNutrition("Protein", result.proteinG))
            add(FruitNutrition("Potassium", result.potassiumMg))
            add(FruitNutrition("Serving Size", result.servingSizeG))
            add(FruitNutrition("Sodium", result.sodiumMg))
            add(FruitNutrition("Sugar", result.sugarG))
        }
    }

    fun getFruitNutrition(): LiveData<List<FruitNutrition>> = liveData {
        emit(fruitNutrition)
    }
}