package com.adamcoding.prototype.ui.main.camchooser

import androidx.lifecycle.ViewModel
import com.adamcoding.prototype.domain.repository.ClassificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CamChooserViewModel @Inject constructor(
    private val classificationRepository: ClassificationRepository,
) : ViewModel() {

    fun predict(imageFile: File) = classificationRepository.predict(imageFile)
}