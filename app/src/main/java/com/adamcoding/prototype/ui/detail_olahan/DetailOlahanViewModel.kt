package com.adamcoding.prototype.ui.detail_olahan

import androidx.lifecycle.ViewModel
import com.adamcoding.prototype.domain.repository.ClassificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailOlahanViewModel @Inject constructor(
    private val classificationRepository: ClassificationRepository,
) : ViewModel() {

    fun getProduct(id: Int) = classificationRepository.getProductById(id)
}