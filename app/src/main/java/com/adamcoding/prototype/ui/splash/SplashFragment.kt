package com.adamcoding.prototype.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.adamcoding.prototype.R
import com.adamcoding.prototype.ui.login.LoginActivity
import com.adamcoding.prototype.ui.main.MainActivity

class SplashFragment : Fragment() {

    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutResId = when (position) {
            0 -> R.layout.fragment_first_splash
            1 -> R.layout.fragment_second_splash
            2 -> R.layout.fragment_third_splash
            else -> throw IllegalArgumentException("Invalid position: $position")
        }

        return inflater.inflate(layoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (position) {
            0 -> {
                val skipButton: Button = view.findViewById(R.id.button_skip_first_splash)
                skipButton.setOnClickListener {
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
            }
            1 -> {
                val skipButton: Button = view.findViewById(R.id.button_skip_second_splash)
                skipButton.setOnClickListener {
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
            }
            2 -> {
                val skipButton: Button = view.findViewById(R.id.button_skip_third_splash)
                val nextButton: Button = view.findViewById(R.id.button_next)
                skipButton.setOnClickListener {
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
                nextButton.setOnClickListener {
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
            }
        }
    }

    fun setPosition(position: Int) {
        this.position = position
    }
}