package com.adamcoding.prototype.ui.main.camchooser

import android.app.Activity
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.adamcoding.prototype.data.remote.response.PredictResponse
import com.adamcoding.prototype.databinding.FragmentCamChooserBinding
import com.adamcoding.prototype.ui.classificator.FreshImageClassifier
import com.adamcoding.prototype.util.Result
import com.adamcoding.prototype.util.toBitmap
import com.adamcoding.prototype.util.toFile
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CamChooserFragment : Fragment() {

    private var _binding: FragmentCamChooserBinding? = null
    private val binding get() = _binding
    private val freshImageClassifier by lazy {
        FreshImageClassifier(requireActivity())
    }
    private val viewModel by viewModels<CamChooserViewModel>()
    private val loadingDialog by lazy {
        AlertDialog.Builder(requireActivity())
            .setMessage("Mohon tunggu, sedang diproses...")
            .setTitle("Processing")
            .setCancelable(false)
            .create()
    }
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCamChooserBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            btnKlasifikasi.setOnClickListener {
                ImagePicker.with(this@CamChooserFragment)
                    .maxResultSize(100, 100)
                    .createIntent {
                        imageChooserClassification.launch(it)
                    }
            }
            btnFreshness.setOnClickListener {
                ImagePicker.with(this@CamChooserFragment)
                    .createIntent {
                        imageChooserFreshness.launch(it)
                    }
            }
        }
    }

    private fun predictClassificationObserver(freshStatus: String? = null) = Observer<Result<PredictResponse>> { result ->
        when (result) {
            is Result.Error -> {
                loadingDialog.dismiss()
                Toast.makeText(requireActivity(), result.message, Toast.LENGTH_LONG).show()
            }
            is Result.Loading -> {
                loadingDialog.show()
            }
            is Result.Success -> {
                loadingDialog.dismiss()
                if (freshStatus.isNullOrEmpty()) {
                    val toDetail = CamChooserFragmentDirections.actionCamChooserFragmentToFruitResultActivity(imageUri)
                    toDetail.data = result.data
                    findNavController().navigate(toDetail)
                } else {
                    val toDetail = CamChooserFragmentDirections.actionCamChooserFragmentToFreshnesActivity(result.data, imageUri)
                    toDetail.freshStatus = freshStatus == "Fresh"
                    findNavController().navigate(toDetail)
                }
            }
        }
    }

    private val imageChooserClassification = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val resultCode = result.resultCode
        val data = result.data

        when (resultCode) {
            Activity.RESULT_OK -> {
                val file = data?.data?.toFile(requireActivity())
                imageUri = data?.data
                if (file != null) {
                    viewModel.predict(file)
                        .observe(viewLifecycleOwner, predictClassificationObserver())
                }
            }

            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(requireActivity(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                    .show()
            }

            else -> {
                Toast.makeText(requireActivity(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val imageChooserFreshness = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val resultCode = result.resultCode
        val data = result.data

        when (resultCode) {
            Activity.RESULT_OK -> {
                val bitmap = data?.data?.toBitmap(requireActivity())
                val imageFile = data?.data?.toFile(requireActivity())
                imageUri = data?.data
                if (bitmap != null) {
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
                        freshImageClassifier.classifyImage(bitmap)
                        val classifierResult = freshImageClassifier.showResult()

                        withContext(Dispatchers.Main) {
                            if (imageFile != null) {
                                viewModel.predict(imageFile)
                                    .observe(viewLifecycleOwner, predictClassificationObserver(classifierResult))
                            }
                        }
                    }
                }
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(requireActivity(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(requireActivity(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}