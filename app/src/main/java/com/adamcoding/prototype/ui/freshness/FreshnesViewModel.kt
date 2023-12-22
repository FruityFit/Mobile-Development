package com.adamcoding.prototype.ui.freshness

import androidx.lifecycle.ViewModel
import com.adamcoding.prototype.domain.repository.ClassificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FreshnesViewModel @Inject constructor(
    private val classificationRepository: ClassificationRepository,
) : ViewModel() {

    fun getProducts(fruitName: String) = classificationRepository.getProducts(fruitName)
}