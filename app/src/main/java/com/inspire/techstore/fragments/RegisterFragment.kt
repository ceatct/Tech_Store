package com.inspire.techstore.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.inspire.techstore.R
import com.inspire.techstore.fragments.models.RegisterFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this)[RegisterFragmentViewModel::class.java] }
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private lateinit var registrationButton: Button
    private lateinit var loginButton: Button
    private lateinit var emailEditTextRegistration: TextInputEditText
    private lateinit var usernameEditTextRegistration: TextInputEditText
    private lateinit var passwordEditTextRegistration: TextInputEditText
    private lateinit var nameEditTextRegistration: TextInputEditText
    private lateinit var lastnameEditTextRegistration: TextInputEditText
    private lateinit var phoneEditTextRegistration: TextInputEditText
    private lateinit var usernameEditTextLogin: TextInputEditText
    private lateinit var passwordEditTextLogin: TextInputEditText
    private lateinit var loginTextView: TextView
    private lateinit var alreadyInfoLayout: LinearLayout
    private lateinit var havenInfoLayout: LinearLayout
    private lateinit var loginInputLayout: LinearLayout
    private lateinit var registrationInputLayout: LinearLayout
    private lateinit var backActionLayout: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        registrationButton = view.findViewById(R.id.registration)
        loginButton = view.findViewById(R.id.login_bt)
        emailEditTextRegistration = view.findViewById(R.id.email)
        usernameEditTextRegistration = view.findViewById(R.id.username)
        passwordEditTextRegistration = view.findViewById(R.id.password)
        nameEditTextRegistration = view.findViewById(R.id.name)
        lastnameEditTextRegistration = view.findViewById(R.id.lastname)
        phoneEditTextRegistration = view.findViewById(R.id.phone)
        usernameEditTextLogin = view.findViewById(R.id.username_login)
        passwordEditTextLogin = view.findViewById(R.id.password_login)
        loginTextView = view.findViewById(R.id.login)
        registrationInputLayout = view.findViewById(R.id.registration_ll)
        alreadyInfoLayout = view.findViewById(R.id.already_ll)
        havenInfoLayout = view.findViewById(R.id.havent_ll)
        loginInputLayout = view.findViewById(R.id.login_ll)
        backActionLayout = view.findViewById(R.id.back)

        val header = requireActivity().findViewById<ViewGroup>(R.id.include)
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        header.visibility = View.GONE
        bottomNavigationView.visibility = View.GONE

        backActionLayout.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragments, ProfileFragment())
            fragmentTransaction.commit()
            header.visibility = View.VISIBLE
            bottomNavigationView.visibility = View.VISIBLE
        }

        loginTextView.setOnClickListener {
            registrationInputLayout.visibility = View.GONE
            alreadyInfoLayout.visibility = View.GONE
            havenInfoLayout.visibility = View.VISIBLE
            loginInputLayout.visibility = View.VISIBLE
        }

        havenInfoLayout.setOnClickListener {
            havenInfoLayout.visibility = View.GONE
            loginInputLayout.visibility = View.GONE
            registrationInputLayout.visibility = View.VISIBLE
            alreadyInfoLayout.visibility = View.VISIBLE
        }

        val allFieldsFilled = listOf(
            emailEditTextRegistration.text.toString(),
            usernameEditTextRegistration.text.toString(),
            passwordEditTextRegistration.text.toString(),
            nameEditTextRegistration.text.toString(),
            lastnameEditTextRegistration.text.toString(),
            phoneEditTextRegistration.text.toString()
        ).all { it.isNotEmpty() }

        val allFieldsFilledLogin = listOf(
            usernameEditTextLogin.text.toString(),
            passwordEditTextLogin.text.toString()
        ).all { it.isNotEmpty() }

        registrationButton.setOnClickListener {
            if (allFieldsFilled) {
                coroutineScope.launch {
                    viewModel.register(
                        emailEditTextRegistration.text.toString(),
                        usernameEditTextRegistration.text.toString(),
                        passwordEditTextRegistration.text.toString(),
                        nameEditTextRegistration.text.toString(),
                        lastnameEditTextRegistration.text.toString(),
                        phoneEditTextRegistration.text.toString(), requireContext()
                    )
                }
            } else {
                Toast.makeText(requireContext(), R.string.fill, Toast.LENGTH_SHORT).show()
            }
        }

        loginButton.setOnClickListener {
            if (allFieldsFilledLogin) {
                coroutineScope.launch {
                    // viewModel.register(email.toString(), username.toString(), password.toString(), name.toString(), lastname.toString(), phone.toString(), requireContext())
                }
            } else Toast.makeText(requireContext(), R.string.fill, Toast.LENGTH_SHORT).show()
        }

        return view
    }
}