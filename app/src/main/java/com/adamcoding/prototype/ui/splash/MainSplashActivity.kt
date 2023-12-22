package com.adamcoding.prototype.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.adamcoding.prototype.R

class MainSplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_splash)

        Handler().postDelayed({
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}