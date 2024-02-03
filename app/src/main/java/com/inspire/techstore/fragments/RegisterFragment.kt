package com.inspire.techstore.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.inspire.techstore.R
import com.inspire.techstore.fragments.models.RegisterFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[RegisterFragmentViewModel::class.java]
    }

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private lateinit var registration: Button
    private lateinit var login_bt: Button

    private lateinit var emailet:    TextInputEditText
    private lateinit var usernameet: TextInputEditText
    private lateinit var passwordet: TextInputEditText
    private lateinit var nameet:     TextInputEditText
    private lateinit var lastnameet: TextInputEditText
    private lateinit var phoneet:    TextInputEditText

    private lateinit var username_login:    TextInputEditText
    private lateinit var password_login:    TextInputEditText

    private lateinit var login:    TextView

    private lateinit var registration_ll:    LinearLayout
    private lateinit var already_ll:    LinearLayout
    private lateinit var havent_ll:    LinearLayout
    private lateinit var login_ll:    LinearLayout

    private lateinit var back : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_register, container, false)

        registration = view.findViewById(R.id.registration)
        login_bt = view.findViewById(R.id.login_bt)

        emailet = view.findViewById(R.id.email)
        usernameet = view.findViewById(R.id.username)
        passwordet = view.findViewById(R.id.password)
        nameet = view.findViewById(R.id.name)
        lastnameet = view.findViewById(R.id.lastname)
        phoneet = view.findViewById(R.id.phone)

        username_login = view.findViewById(R.id.username_login)
        password_login = view.findViewById(R.id.password_login)

        login = view.findViewById(R.id.login)
        registration_ll = view.findViewById(R.id.registration_ll)
        already_ll = view.findViewById(R.id.already_ll)
        havent_ll = view.findViewById(R.id.havent_ll)
        login_ll = view.findViewById(R.id.login_ll)

        back = view.findViewById(R.id.back)

        back.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStackImmediate()
        }

        login.setOnClickListener {
            registration_ll.visibility = GONE
            already_ll.visibility = GONE

            havent_ll.visibility = VISIBLE
            login_ll.visibility = VISIBLE
        }

        havent_ll.setOnClickListener {
            havent_ll.visibility = GONE
            login_ll.visibility = GONE

            registration_ll.visibility = VISIBLE
            already_ll.visibility = VISIBLE
        }

        val allFieldsFilled = listOf(
            emailet.text.toString(),  // Access text properties directly
            usernameet.text.toString(),
            passwordet.text.toString(),
            nameet.text.toString(),
            lastnameet.text.toString(),
            phoneet.text.toString()
        ).all { it.isNotEmpty() }

        val allFieldsFilledLogin = listOf(username_login.text.toString(), password_login.text.toString()).all { it.isNotEmpty() }

        registration.setOnClickListener {
            if (allFieldsFilled) {
                coroutineScope.launch {
                    viewModel.register(emailet.text.toString(), usernameet.text.toString(), passwordet.text.toString(), nameet.text.toString(), lastnameet.text.toString(), phoneet.text.toString(), requireContext())
                }
            } else {
                Toast.makeText(requireContext(), R.string.fill, Toast.LENGTH_SHORT).show()
            }
        }

        login_bt.setOnClickListener{

            if(allFieldsFilledLogin){
                coroutineScope.launch {
                   // viewModel.register(email.toString(), username.toString(), password.toString(), name.toString(), lastname.toString(), phone.toString(), requireContext())
                }
            }
            else Toast.makeText(requireContext(), R.string.fill, Toast.LENGTH_SHORT).show()

        }

        return view
    }

}