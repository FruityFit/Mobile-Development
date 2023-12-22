package com.adamcoding.prototype.ui.fruit_result

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.exifinterface.media.ExifInterface
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.adamcoding.prototype.databinding.ActivityFruitResultBinding
import com.adamcoding.prototype.ui.adapter.NutritionAdapter
import com.adamcoding.prototype.ui.adapter.artikelOlahanAdapter
import com.adamcoding.prototype.ui.detail_olahan.DetailOlahanActivity
import com.adamcoding.prototype.util.Result
import com.iamageo.library.AnotherReadMore
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class FruitResultActivity : AppCompatActivity() {

    private val navArgs by navArgs<FruitResultActivityArgs>()
    private val binding by lazy {
        ActivityFruitResultBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<FruitResultViewModel>()
    private val nutritionAdapter by lazy {
        NutritionAdapter()
    }
    private val olahanAdapter by lazy {
        artikelOlahanAdapter {
            val intent = Intent(this, DetailOlahanActivity::class.java).apply {
                putExtra(DetailOlahanActivity.EXTRA_OLAHAN, it.productId)
            }
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            val fruitData = navArgs.data
            hasilDetect.setImageURI(navArgs.imageUri)

            val exif = navArgs.imageUri?.path?.let { ExifInterface(it) }

            val rotationDegrees = when (exif?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                else -> 0
            }

            hasilDetect.rotation = rotationDegrees.toFloat()

            if (fruitData != null) {
                namaBuah.text = fruitData.name?.uppercase()
                skor.text = ((fruitData.confidence ?: 0.0) * 100.0).roundToInt().toString() + "%"
                val anotherReadMore = AnotherReadMore.Builder()
                    .moreLabel("...Selengkapnya")
                    .lessLabel("...Lihat sedikit")
                    .textLengthType(AnotherReadMore.TYPE_CHARACTER)
                    .build()

                anotherReadMore.addReadMoreTo(isiDesc,  fruitData.description.toString())
                viewModel.addNutrition(fruitData)
            }

            rvNutrisi.apply {
                layoutManager = LinearLayoutManager(this@FruitResultActivity, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = nutritionAdapter
            }
            rvOlahan.apply {
                layoutManager = LinearLayoutManager(this@FruitResultActivity, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = olahanAdapter
            }
            btnBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            viewModel.getFruitNutrition().observe(this@FruitResultActivity) {
                nutritionAdapter.submitList(it)
            }
            viewModel.getProducts(fruitData?.name.toString()).observe(this@FruitResultActivity) {
                when (it) {
                    is Result.Error -> {}
                    is Result.Loading -> {}
                    is Result.Success -> {
                        olahanAdapter.submitList(it.data)
                    }
                }
            }
        }
    }
}