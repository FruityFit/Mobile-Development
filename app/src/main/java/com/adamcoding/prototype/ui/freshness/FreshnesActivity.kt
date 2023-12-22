package com.adamcoding.prototype.ui.freshness

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.adamcoding.prototype.databinding.ActivityFreshnesBinding
import com.adamcoding.prototype.ui.adapter.artikelOlahanAdapter
import com.adamcoding.prototype.ui.detail_olahan.DetailOlahanActivity
import com.adamcoding.prototype.util.Result
import com.iamageo.library.AnotherReadMore
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class FreshnesActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFreshnesBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<FreshnesViewModel>()
    private val olahanAdapter by lazy {
        artikelOlahanAdapter {
            val intent = Intent(this, DetailOlahanActivity::class.java).apply {
                putExtra(DetailOlahanActivity.EXTRA_OLAHAN, it.productId)
            }
            startActivity(intent)
        }
    }
    private val navArgs by navArgs<FreshnesActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            val fruitData = navArgs.data
            hasilDetect.setImageURI(navArgs.imageUri)
            if (fruitData != null) {
                namaBuah.text = fruitData.name?.uppercase()
                skor.text = ((fruitData.confidence ?: 0.0) * 100.0).roundToInt().toString() + "%"
                val anotherReadMore = AnotherReadMore.Builder()
                    .moreLabel("...Selengkapnya")
                    .lessLabel("...Lihat sedikit")
                    .textLengthType(AnotherReadMore.TYPE_CHARACTER)
                    .build()

                anotherReadMore.addReadMoreTo(isiDesc,  fruitData.description.toString())
            }
            angkaKesegaran.text = if (navArgs.freshStatus) "Segar" else "Tidak Segar"
            rvOlahan.apply {
                layoutManager = LinearLayoutManager(this@FreshnesActivity, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = olahanAdapter
            }
            btnBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            viewModel.getProducts(fruitData?.name.toString()).observe(this@FreshnesActivity) {
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