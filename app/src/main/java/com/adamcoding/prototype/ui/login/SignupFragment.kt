package com.adamcoding.prototype.ui.login

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.adamcoding.prototype.R
import com.adamcoding.prototype.data.remote.retrofit.ApiService
import com.adamcoding.prototype.data.remote.retrofit.RetrofitClient
import com.adamcoding.prototype.data.repository.UserRepository
import com.adamcoding.prototype.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {
    private val apiService = RetrofitClient.createService(ApiService::class.java)
    private val userRepository = UserRepository(apiService)
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameEditText = view.findViewById<EditText>(R.id.input_username)
        val emailEditText = view.findViewById<EditText>(R.id.input_email)
        val passwordEditText = view.findViewById<EditText>(R.id.input_passwordSignUp)
        val signUpButton = view.findViewById<Button>(R.id.button_signup)
        val showHidePassword = view.findViewById<ImageView>(R.id.showHidePassword)

        passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()

        showHidePassword.setOnClickListener {
            val isVisible = passwordEditText.transformationMethod == PasswordTransformationMethod.getInstance()
            passwordEditText.transformationMethod =
                if (isVisible) HideReturnsTransformationMethod.getInstance()
                else PasswordTransformationMethod.getInstance()

            showHidePassword.setImageResource(
                if (isVisible) R.drawable.ic_eye_close
                else R.drawable.ic_eye_open
            )
        }

        signUpButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (validateInputs(username, email, password)) {
                userRepository.signUp(username, email, password) { success, message ->
                    if (success) {
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        val viewPager = requireActivity().findViewById<ViewPager2>(R.id.view_pager_login)
                        viewPager?.setCurrentItem(0, true)
                    } else {
                        Toast.makeText(requireContext(), "$message", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun validateInputs(username: String, email: String, password: String): Boolean {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.length < 8) {
            Toast.makeText(requireContext(), "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
            return false
        }

        val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        if (!email.matches(emailRegex)) {
            Toast.makeText(requireContext(), "Invalid email format", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    companion object {
        fun newInstance(sectionNumber: Int): SignupFragment {
            val fragment = SignupFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }

        private const val ARG_SECTION_NUMBER = "section_number"
    }
}