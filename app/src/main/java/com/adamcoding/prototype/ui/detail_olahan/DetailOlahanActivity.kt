package com.adamcoding.prototype.ui.detail_olahan

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.adamcoding.prototype.data.remote.response.DetailProductResponse
import com.adamcoding.prototype.databinding.ActivityDetailOlahanBinding
import com.adamcoding.prototype.util.Result
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailOlahanActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetailOlahanBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<DetailOlahanViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getIntExtra(EXTRA_OLAHAN, 0)
        viewModel.getProduct(id).observe(this) {
            when (it) {
                is Result.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {}
                is Result.Success -> { populateDetail(it.data) }
            }
        }
    }

    private fun populateDetail(data: DetailProductResponse) {
        binding.apply {
            tvTitle.text = data.name
            tvDesc.text = data.description
            tvRecipe.text = data.recipe

            Glide.with(this@DetailOlahanActivity)
                .load(data.imageUrl)
                .into(ivContent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_OLAHAN = "extra_olahan"
    }
}