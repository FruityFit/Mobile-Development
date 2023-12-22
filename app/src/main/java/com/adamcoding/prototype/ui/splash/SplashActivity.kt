package com.adamcoding.prototype.ui.splash

import android.os.Bundle
import android.os.Handler
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.adamcoding.prototype.R

class SplashActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewPager = findViewById(R.id.viewPager)

        val adapter = SplashPagerAdapter(supportFragmentManager)
        adapter.addFragment(SplashFragment().apply { setPosition(0) })
        adapter.addFragment(SplashFragment().apply { setPosition(1) })
        adapter.addFragment(SplashFragment().apply { setPosition(2) })

        viewPager.adapter = adapter
    }
}