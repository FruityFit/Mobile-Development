package com.adamcoding.prototype.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.adamcoding.prototype.R
import com.adamcoding.prototype.data.remote.request.SignInRequest
import com.adamcoding.prototype.data.remote.response.SignInResponse
import com.adamcoding.prototype.data.remote.retrofit.ApiService
import com.adamcoding.prototype.data.remote.retrofit.RetrofitClient
import com.adamcoding.prototype.databinding.FragmentSigninBinding
import com.adamcoding.prototype.databinding.FragmentSignupBinding
import com.adamcoding.prototype.ui.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SigninFragment : Fragment() {
    private val apiService = RetrofitClient.createService(ApiService::class.java)
    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences
    private var savedEmail: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Initialize SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences(
            "RememberMePrefs",
            Context.MODE_PRIVATE
        )

        savedEmail = sharedPreferences.getString(KEY_SAVED_EMAIL, null)
        savedEmail?.let {
            binding.inputEmailOrUsername.setText(it)
            binding.checkboxRememberMe.setImageResource(R.drawable.ic_checked) // Set checked state
        }

        super.onViewCreated(view, savedInstanceState)

        val emailOrUsernameEditText = view.findViewById<EditText>(R.id.input_email_or_username)
        val passwordEditText = view.findViewById<EditText>(R.id.input_password)
        val signInButton = view.findViewById<Button>(R.id.button_signin)
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

        val rememberMeCheckbox = view.findViewById<ImageView>(R.id.checkboxRememberMe)

        passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
        showHidePassword.setImageResource(R.drawable.ic_eye_open)

        showHidePassword.setOnClickListener {
            val isVisible =
                passwordEditText.transformationMethod == PasswordTransformationMethod.getInstance()
            passwordEditText.transformationMethod =
                if (isVisible) HideReturnsTransformationMethod.getInstance()
                else PasswordTransformationMethod.getInstance()

            showHidePassword.setImageResource(
                if (isVisible) R.drawable.ic_eye_close
                else R.drawable.ic_eye_open
            )
        }

        rememberMeCheckbox.setOnClickListener {
            if (savedEmail == null) {
                // Save the email
                savedEmail = emailOrUsernameEditText.text.toString()
                with(sharedPreferences.edit()) {
                    putString(KEY_SAVED_EMAIL, savedEmail)
                    apply()
                }
                rememberMeCheckbox.setImageResource(R.drawable.ic_checked)
            } else {
                with(sharedPreferences.edit()) {
                    remove(KEY_SAVED_EMAIL)
                    apply()
                }
                savedEmail = null
                rememberMeCheckbox.setImageResource(R.drawable.ic_uncheck)
            }
        }

        signInButton.setOnClickListener {
            val emailOrUsername = emailOrUsernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            signIn(emailOrUsername, password)
        }
    }

    private fun signIn(identifier: String, password: String) {
        val signInRequest = SignInRequest(identifier, password)

        apiService.signIn(signInRequest).enqueue(object : Callback<SignInResponse> {
            override fun onResponse(
                call: Call<SignInResponse>,
                response: Response<SignInResponse>
            ) {
                if (response.isSuccessful) {
                    val signInResponse = response.body()
                    Toast.makeText(requireContext(), signInResponse?.message, Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    Toast.makeText(requireContext(), "Proses masuk gagal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        fun newInstance(sectionNumber: Int): SigninFragment {
            val fragment = SigninFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            fragment.arguments = args
            return fragment
        }

        const val KEY_SAVED_EMAIL = "saved_email"
        private const val ARG_SECTION_NUMBER = "section_number"
    }
}